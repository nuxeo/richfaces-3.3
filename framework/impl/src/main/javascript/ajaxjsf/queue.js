A4J.AJAX._eventQueues = {};

//queue constructor
A4J.AJAX.EventQueue = function() {
	var DROP_NEW = 'dropNew';
	var DROP_NEXT = 'dropNext';
	var FIRE_NEW = 'fireNew';
	var FIRE_NEXT = 'fireNext';

	var extend = function(target, source) {
		for (var property in source) {
			target[property] = source[property];
		}
	};

	var extendOptions = function(options) {
		var opts = {};
		
		for (var name in options) {
			opts[name] = options[name];
		}
		
		for (var name in this.requestOptions) {
			if (typeof opts[name] == 'undefined') {
				opts[name] = this.requestOptions[name];
			}
 		}
		
		return opts;
	};
	
	var QueueEntry = function() {
		var ctor = function(queue, query, options, event) {
			this.queue = queue;
			this.query = query;
			this.options = options;
			this.event = event;
			
			this.similarityGroupingId = this.options.similarityGroupingId;
			this.eventsCount = 1;
		};

		extend(ctor.prototype, {
			
			isIgnoreDupResponses: function() {
				return this.options.ignoreDupResponses;
			},
			
			getSimilarityGroupingId: function() {
				return this.similarityGroupingId;
			},
			
			setSimilarityGroupingId: function(id) {
				this.similarityGroupingId = id;
			},
	
			submit: function() {
				this.query.appendParameter("AJAX:EVENTS_COUNT", this.eventsCount);
				this.request = A4J.AJAX.SubmitQuery(this.query, this.options, this.event)
				
				var queue = this.queue; // TODO: move to next line
				this.request.queue = queue;
	
				return this.request;
			},
			
			abort: function() {
				if (this.request && !this.aborted) {
					//tell request not to notify queue after its processing finished
					//request.queue = undefined 
					//is not ok for this case because user might want to obtain queue in any event handler

					//RF-5788
					this.aborted = true;
					//this.request.shouldNotifyQueue = false;
					this.request.abort();
					//this.request = undefined;
				}
			},
			
			ondrop: function() {
				var callback = this.options.onqueuerequestdrop;
				if (callback) {
					callback.call(this.queue, this.query, this.options, this.event);
				}
			},
			
			onRequestDelayPassed: function() {
				this.readyToSubmit = true;
				this.queue.submitFirst();
			},
			
			startTimer: function() {
				var delay = this.options.requestDelay;
				
				LOG.debug("Queue will wait " + (delay || 0) + "ms before submit");

				if (delay) {
					var _this = this;
					this.timer = setTimeout(function() {
						try {
							_this.onRequestDelayPassed();
						} finally {
							_this.timer = undefined;
							_this = undefined;
						}
					}, delay);
				} else {
					this.onRequestDelayPassed();
				}
			},
			
			stopTimer: function() {
				if (this.timer) {
					clearTimeout(this.timer);
					this.timer = undefined;
				}
			},
			
			clearEntry: function() {
				this.stopTimer();
				if (this.request) {
					this.request.shouldNotifyQueue = false;
					this.request = undefined;
				}
			},
			
			getEventsCount: function() {
				return this.eventsCount;
			},
			
			setEventsCount: function(newCount) {
				this.eventsCount = newCount;
			},
			
			getEventArguments: function() {
				return [this.query, this.options, this.event];
			}
		});
		
		return ctor;
	}();
	
	var Queue = function(name, queueOptions, requestOptions) {
		this.items = new Array();

		this.name = name;
		this.queueOptions = queueOptions || {};
		this.requestOptions = requestOptions || {};
	};
	
	extend(Queue.prototype, {

		submitFirst: function() {
			var firstItem = this.items[0];
			if (firstItem) {
				if (!firstItem.request) {
					if (firstItem.readyToSubmit) {
						LOG.debug("Queue '" + this.name + "' will submit request NOW");

						var req = firstItem.submit();

						//see entry.abort() method for more details about this assignment
						req.shouldNotifyQueue = true;

						if (this.requestOptions.queueonsubmit) {
							this.requestOptions.queueonsubmit.call(this, req);
						}
					} else {
						LOG.debug("First item is not ready to be submitted yet");
					}
				}
			} else {
				LOG.debug("Queue is empty now");
			}
		},
		
		getSize: function() {
			return this.items.length;
		},
		
		getMaximumSize: function() {
			return this.queueOptions.size;
		},
		
		isFull: function() {
			return this.getSize() == this.getMaximumSize();
		},
		
		getSizeExceededBehavior: function() {
			var policy = this.queueOptions.sizeExceededBehavior;
			if (!policy) {
				policy = DROP_NEXT;
			}

			return policy;
		},
		
		queue: function(entry) {
			this.items.push(entry);
			
			if (this.queueOptions.onrequestqueue) {
				LOG.debug("Call onrequestqueue handler");
				this.queueOptions.onrequestqueue.apply(this, entry.getEventArguments());
			}
		},
		
		dequeue: function() {
			var entry = this.items.shift();
			
			if (this.queueOptions.onrequestdequeue) {
				LOG.debug("Call onrequestdequeue handler");
				this.queueOptions.onrequestdequeue.apply(this, entry.getEventArguments());
			}
		},

		push: function(query, opts, event) {
			var options = extendOptions.call(this, opts);
			
			var entry = new QueueEntry(this, query, options, event);
			var similarityGroupingId = entry.getSimilarityGroupingId();
			
			var lastIdx = this.items.length - 1;
			var last = this.items[lastIdx];
			var handled = false;
			
			if (last) {
				if (last.getSimilarityGroupingId() == similarityGroupingId) {
					LOG.debug("Similar request currently in queue '" + this.name + "'");

					if (last.request) {
						LOG.debug("Request has already beeen sent to server");
						if (entry.isIgnoreDupResponses()) {
							LOG.debug("Duplicate responses ignore requested");

							if (!this.isFull()) {
								last.abort();
								
								LOG.debug("Response for the current request will be ignored");
								
								/* Change for - RF-5788 - wait current request to complete even if ignore duplicate is true
								//remove last (that is actually first) from queue - will be safer to do that in LinkedList
								this.dequeue();
								*/
							} else {
								LOG.debug("Queue is full, cannot set to ignore response for the current request");
							}
							
						}
					} else {
						LOG.debug("Combine similar requests and reset timer");
						
						handled = true;
						last.stopTimer();
						entry.setEventsCount(last.getEventsCount() + 1);
						
						this.items[lastIdx] = entry;
						entry.startTimer();
					}
				} else {
					LOG.debug("Last queue entry is not the last anymore. Stopping requestDelay timer and marking entry as ready for submission")
					
					last.stopTimer();
					last.setSimilarityGroupingId(undefined);
					last.readyToSubmit = true;
				}
			}
			
			if (!handled) {
				if (this.isFull()) {
					LOG.debug("Queue '" + this.name + "' is currently full")
					
					var b = this.getSizeExceededBehavior();
					
					var nextIdx = 0;
					while (this.items[nextIdx] && this.items[nextIdx].request) {
						nextIdx++;
					}
					
					if (this.queueOptions.onsizeexceeded) {
						this.queueOptions.onsizeexceeded.apply(this, entry.getEventArguments());
					}
					// TODO: create one function that will be implement this functionality // function (behaviorFlag, entry): should return handled flag
					if (b == DROP_NEW) {
						LOG.debug("Queue '" + this.name + "' is going to drop new item");
						
						entry.ondrop();
						
						handled = true;
					} else if (b == DROP_NEXT) {
						LOG.debug("Queue '" + this.name + "' is going to drop [" + nextIdx + "] item that is the next one");

						var nextEntry = this.items.splice(nextIdx, 1)[0];
						if (nextEntry) {
							LOG.debug("Item dropped from queue");

							nextEntry.stopTimer();

							nextEntry.ondrop();
						} else {
							LOG.debug("There's no such item, will handle new request instead");
							
							entry.ondrop();

							handled = true;
						}
					} else if (b == FIRE_NEW) {
						LOG.debug("Queue '" + this.name + "' will submit new request");
						
						entry.submit();
						handled = true;
					} else if (b == FIRE_NEXT) {
						LOG.debug("Queue '" + this.name + "' is going to drop and fire immediately [" + nextIdx + "] item that is the next one");

						var nextEntry = this.items.splice(nextIdx, 1)[0];
						if (nextEntry) {
							LOG.debug("Item dropped from queue");
							nextEntry.stopTimer();
							nextEntry.submit();
						} else {
							LOG.debug("There's no such item, will handle new request instead");
							entry.submit();
							handled = true;
						}
					}
				}

				this.submitFirst();
			}

			if (!handled) {
				this.queue(entry);

				LOG.debug("New request added to queue '" + this.name + "'. Queue similarityGroupingId changed to " + similarityGroupingId);
				
				entry.startTimer();
			}
		},

		pop: function() {
			LOG.debug("After request: queue '" + this.name + "'");

			this.dequeue();
			
			LOG.debug("There are " + this.items.length + " requests more in this queue");
			
			this.submitFirst();
		},
		
		clear: function() {
			var length = this.items.length;
			for ( var i = 0; i < this.items.length; i++) {
				this.items[i].clearEntry();
			}
			
			this.items.splice(0, length);
		}
	});
	
	return Queue;
}();

A4J.AJAX.EventQueue.DEFAULT_QUEUE_NAME = "org.richfaces.queue.global";

A4J.AJAX.EventQueue.getQueue = function(name) {
	return A4J.AJAX._eventQueues[name];
};

A4J.AJAX.EventQueue.getQueues = function() {
	return A4J.AJAX._eventQueues;
};

A4J.AJAX.EventQueue.addQueue = function(queue) {
	var name = queue.name;
	
	if (A4J.AJAX._eventQueues[name]) {
		throw "Queue already registered";
	} else {
		LOG.debug("Adding queue '" + name + "' to queues registry");
		A4J.AJAX._eventQueues[name] = queue;
	}
};

A4J.AJAX.EventQueue.removeQueue = function(name) {
	var queue = A4J.AJAX._eventQueues[name];
	
	if (queue) {
		queue.clear();
	}
	
	delete A4J.AJAX._eventQueues[name];
};

A4J.AJAX.EventQueue.getOrCreateQueue = function(){
	var qualifyName = function(name, prefix) {
		if (prefix) {
			return prefix + ":" + name;
		} else {
			return name;
		}
	};

	var qualifyNamespace = function(name, prefix) {
		if (prefix) {
			return prefix + name;
		} else {
			return name;
		}
	};
	
	return function(options, formId) {
		var queueName = options.eventsQueue;
		var namespace = options.namespace;

		var formQueueName;
		var viewQueueName;
		var implicitQueueName;
		
		if (queueName) {
			LOG.debug("Look up queue with name '" + queueName + "'");
			
			formQueueName = qualifyName(queueName, formId);
			viewQueueName = qualifyNamespace(queueName, namespace);
			
			implicitQueueName = viewQueueName;
		} else {
			LOG.debug("Look up queue with default name");

			formQueueName = formId;
			viewQueueName = qualifyNamespace(A4J.AJAX.EventQueue.DEFAULT_QUEUE_NAME, namespace);
		
			implicitQueueName = options.implicitEventsQueue;
		}
		
		var queue = A4J.AJAX._eventQueues[formQueueName];
		if (!queue) {
			queue = A4J.AJAX._eventQueues[viewQueueName];
			if (!queue) {
				if (implicitQueueName) {
					queue = A4J.AJAX._eventQueues[implicitQueueName];
					if (!queue) {
						LOG.debug("Creating new transient queue '" + implicitQueueName + "' with default settings");
						queue = new A4J.AJAX.EventQueue(implicitQueueName);
						queue._transient = true;
						
						A4J.AJAX.EventQueue.addQueue(queue);
					} else {
						LOG.debug("Found transient queue '" + implicitQueueName + "'");
					}
				}
			} else {
				LOG.debug("Found view queue '" + viewQueueName + "'");
			}
		} else {
			LOG.debug("Found form queue '" + formQueueName + "'");
		}
		
		return queue;
	}
}();

(function () {
	var observer = function() {
		var queues = A4J.AJAX.EventQueue.getQueues();
		for (var queueName in queues) {
			var queue = queues[queueName];
			queue.clear();
		}
	};
	
	if (window.addEventListener) {
		window.addEventListener("unload", observer, false);
	} else {
		window.attachEvent("onunload", observer);
	}
})();