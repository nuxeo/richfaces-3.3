if (!window.Richfaces) window.Richfaces = {};
(function ($) {

	var RGBColor = function(colors) {
		this.r = Math.min(255, Math.max(0, colors[0]));
		this.g = Math.min(255, Math.max(0, colors[1]));
		this.b = Math.min(255, Math.max(0, colors[2]));
	};
	
	RGBColor.prototype.toHSB = function() {
		if (!this.hsb) {
			var r = this.r, g = this.g, b = this.b;
			var low = Math.min(r, Math.min(g,b));
			var high = Math.max(r, Math.max(g,b));
			
			this.hsb = {b: high*100/255};
			
			var diff = high-low;
			if (diff) {
				this.hsb.s = Math.round(100*(diff/high));
				if (r == high) {
					this.hsb.h = Math.round(((g-b)/diff)*60);
				} else if (g == high) {
					this.hsb.h = Math.round((2 + (b-r)/diff)*60);
				} else {
					this.hsb.h = Math.round((4 + (r-g)/diff)*60);
				}
				
				if (this.hsb.h > 360) {
					this.hsb.h -= 360;
				} else if (this.hsb.h < 0) {
					this.hsb.h += 360;
				}
			} else {
				this.hsb.h = this.hsb.s = 0;
			}
			
			this.hsb.b = Math.round(this.hsb.b);
		}
		
		return this.hsb;
	};

	RGBColor.prototype.toRGB = function() {
		return this;
	};
	
	var HSBColor = function(colors) {
		this.h = Math.min(360, Math.max(0, colors[0]));
		this.s = Math.min(100, Math.max(0, colors[1]));
		this.b = Math.min(100, Math.max(0, colors[2]));
	};
	
	HSBColor.prototype.toHSB = function() {
		return this;
	};
	
	HSBColor.prototype.toRGB = function() {
		if (!this.rgb) {
			this.rgb = {};
			var h = Math.round(this.h);
			var s = Math.round(this.s*255/100);
			var v = Math.round(this.b*255/100);
			
			if (s == 0) {
				this.rgb.r = this.rgb.g = this.rgb.b = v;
			} else {
				var t1 = v;
				var t2 = (255-s) * v / 255;
				var t3 = (t1-t2) * (h%60) / 60;
				if (h == 360) {
					h = 0;
				}
				if (h < 60) {
					this.rgb.r = t1; this.rgb.b = t2; this.rgb.g = t2 + t3;
				} else if (h < 120) {
					this.rgb.g = t1; this.rgb.b = t2; this.rgb.r = t1-t3;
				} else if (h < 180) {
					this.rgb.g=t1; this.rgb.r=t2; this.rgb.b=t2+t3;
				} else if (h < 240) {
					this.rgb.b=t1; this.rgb.r=t2; this.rgb.g=t1-t3;
				} else if (h < 300) {
					this.rgb.b=t1; this.rgb.g=t2; this.rgb.r=t2+t3;
				} else if (h < 360) {
					this.rgb.r=t1; this.rgb.g=t2; this.rgb.b=t1-t3;
				} else {
					this.rgb.r=0; this.rgb.g=0;	this.rgb.b=0;
				}
			}
			
			this.rgb.r = Math.round(this.rgb.r);
			this.rgb.g = Math.round(this.rgb.g);
			this.rgb.b = Math.round(this.rgb.b);
		}
		
		return this.rgb;
	};
	
	
$.widget("ui.colorPicker", {
	
	_fixColors: function(cff, mode) {
		cff = cff.toLowerCase();
		var pattern = /[0-9]+/g;

		var colors = cff.match(pattern); 

		if (mode == 'hsb') {
			return new HSBColor(colors);
		} else if (mode == 'rgb') {
			return new RGBColor(colors);
		} else if (mode == 'hex') {
			var hex = parseInt(((cff.indexOf('#') > -1) ? cff.substring(1) : cff), 16);
			return new RGBColor([hex >> 16, (hex & 0x00FF00) >> 8, (hex & 0x0000FF)]);
		}
	},
		
	_init: function() {
		this.patternHex=new RegExp();
		this.patternDec=new RegExp();
		this.patternHex.compile("[^0-9A-Fa-f]+");
		this.patternDec.compile("[^0-9]+");
		this.charMin = 65;
		var o = this.options, self = this,
		tpl = $(" + .rich-color-picker-wrapper", this.element);
		
		if (o.color.indexOf('hsb') > -1) {
			this.color = this._fixColors(o.color, 'hsb');// this._fixHSB(this._fixColors(o.color, 'hsb'));
		} else if (o.color.indexOf('rgb') > -1) {
			this.color = this._fixColors(o.color, 'rgb');//this._RGBToHSB(this._fixColors(o.color, 'rgb'));
		} else if (o.color.indexOf('#') > -1) {
			this.color = this._fixColors(o.color, 'hex');
		} else{
			return this;
		}

		this.showEvent = o.showEvent;
		this.picker = $(tpl);
		this.picker[0].component = this;

		this.fields = this.picker.find('input')
						.bind('keydown', function(e) { return self._keyDown.call(self, e); })
						.bind('change', function(e) { return self._select.call(self, e); })
						.bind('blur', function(e) { return self._blur.call(self, e); })
						.bind('focus', function(e) { return self._focus.call(self, e); });



		this.selector = this.picker.find('.rich-color-picker-color').bind('mousedown', function(e) { return self._downSelector.call(self, e); });
		this.selectorIndic = this.selector.find('* *');
		this.hue = this.picker.find('.rich-color-picker-rainbow > *:not(:first)');
		this.picker.find('.rich-color-picker-rainbow').bind('mousedown', function(e) { return self._downHue.call(self, e); });

		this.newColor = this.picker.find('.rich-color-picker-new-color');
		this.currentColor = this.picker.find('.rich-color-picker-current-color');
		this.iconColor = $('.rich-color-picker-icon', this.element);
		
		this.picker.find('.rich-color-picker-submit')
			.bind('mouseenter', function(e) { return self._enterSubmit.call(self, e); })
			.bind('mouseleave', function(e) { return self._leaveSubmit.call(self, e); })
			.bind('click', function(e) { return self._clickSubmit.call(self, e); });

		this.picker.find('.rich-color-picker-cancel')
			.bind('mouseenter', function(e) { return self._enterCancel.call(self, e); })
			.bind('mouseleave', function(e) { return self._leaveCancel.call(self, e); })
			.bind('click', function(e) { return self._clickCancel.call(self, e); });

		this._fillRGBFields(this.color);
		this._fillHSBFields(this.color);
		this._fillHexFields(this.color);
		this._setHue(this.color);
		this._setSelector(this.color);
		this._setCurrentColor(this.color);
		this._setIconColor(this.color);
		this._setNewColor(this.color);

		if (o.flat) {
			this.picker.css({
				position: 'absolute'				
			});
			$(this.element).bind(this.showEvent+".colorPicker", function(e) { return self._show.call(self, e); });
			self._show();
		} else {

			$(this.element).bind(this.showEvent+".colorPicker", function(e) { return self._show.call(self, e); });
		}

	},

	destroy: function() {

		this.picker.remove();
		this.picker[0].component = undefined;
		this.element.removeData("colorPicker").unbind(".colorPicker");

	},

	_fillRGBFields: function(color) {
		var rgb = color.toRGB();
        
		this.fields
			.eq(1).val(rgb.r).end()
			.eq(2).val(rgb.g).end()
			.eq(3).val(rgb.b).end();
	},
	
	_fillHSBFields: function(color) {
		var hsb = color.toHSB();
		
		this.fields
			.eq(4).val(hsb.h).end()
			.eq(5).val(hsb.s).end()
			.eq(6).val(hsb.b).end();
	},
	
	_fillHexFields: function (color) {
		var rgb = color.toRGB();
		
		this.fields
			.eq(0).val(this._RGBToHex(rgb)).end();
	},
	
	_setSelector: function(color) {
		var hsb = color.toHSB();
		this.selector.css('backgroundColor', '#' + this._RGBToHex(new HSBColor([hsb.h,100,100]).toRGB()));
		this.selectorIndic.css({
			left: parseInt(150 * hsb.s/100, 10),
			top: parseInt(150 * (100-hsb.b)/100, 10)
		});
	},
	_setHue: function(color) {
		var hsb = color.toHSB();
		this.hue.css('top', parseInt(150 - 150 * hsb.h/360, 10));
	},
	_setCurrentColor: function(color) {
		this.currentColor.css('backgroundColor', '#' + this._RGBToHex(color.toRGB()));
	},
	_setIconColor: function(color) {
		this.iconColor.css('backgroundColor', '#' + this._RGBToHex(color.toRGB()));
	},
	_setNewColor: function(color) {
		this.newColor.css('backgroundColor', '#' + this._RGBToHex(color.toRGB()));
	},
	
	_keyDown: function(e) {
		var pressedKey = e.charCode || e.keyCode || -1;
		if ((pressedKey > this.charMin && pressedKey <= 90) || pressedKey == 32) {
			return false;
		}
	},
	
	_createEventArgument: function(color) {
		return { options: this.options, 
			hsb: color.toHSB(), 
			hex: this._RGBToHex(color.toRGB()), 
			rgb: color.toRGB() 
		};
	},
	
	_select: function(e, target) {

		var col;
		target = target || e.target;
		
		for (i=0; i <= this.fields.length; i++){
			if(this.fields.eq(i).val() == "" || this.fields.eq(i).val() == null){
				this.fields.eq(i).val(0);
			}
		}
		
		if (target.parentNode.className.indexOf('-hex') > 0) {
			if(this.patternHex.test(target.value)){
				target.value = 0;
			}
		}else{
			if(this.patternDec.test(target.value)){
				target.value = 0;			
			}
		}
		
		if (target.parentNode.className.indexOf('-hex') > 0) {

			this.color = col = this._fixColors(this.fields.eq(0).val(), 'hex');
			this._fillHexFields(col);
			this._fillRGBFields(col);
			this._fillHSBFields(col);
		} else if (target.parentNode.className.indexOf('-hsb') > 0) {
			this.color = col = new HSBColor([
					parseInt(this.fields.eq(4).val(), 10), 
					parseInt(this.fields.eq(5).val(), 10),
					parseInt(this.fields.eq(6).val(), 10)]);

			this._fillHSBFields(col);
			this._fillRGBFields(col);
			this._fillHexFields(col);
		} else {
			this.color = col = new RGBColor([
			        parseInt(this.fields.eq(1).val(), 10),
			        parseInt(this.fields.eq(2).val(), 10),
			        parseInt(this.fields.eq(3).val(), 10)]);
			
			this._fillRGBFields(col);
			this._fillHexFields(col);
			this._fillHSBFields(col);
		}
		this._setSelector(col); 
		this._setHue(col);
		this._setNewColor(col);
		this._trigger('select', e, this._createEventArgument(col));
	},
	_blur: function(e) {
		this.fields.parent().removeClass('rich-color-picker-focus');

	},
	_focus: function(e) {

		this.charMin = e.target.parentNode.className.indexOf('-hex') > 0 ? 70 : 65;
		this.fields.parent().removeClass('rich-color-picker-focus');
		$(e.target.parentNode).addClass('rich-color-picker-focus');

	},
	_downHue: function(e) {

		this.currentHue = {
			y: this.picker.find('.rich-color-picker-rainbow').offset().top
		};

		this._select.apply(this, [e, this
				.fields
				.eq(4)
				.val(parseInt(360*(150 - Math.max(0,Math.min(150,(e.pageY - this.currentHue.y))))/150, 10))
				.get(0)]);

		var self = this;
		$(document).bind('mouseup.cpSlider', function(e) { return self._upHue.call(self, e); });
		$(document).bind('mousemove.cpSlider', function(e) { return self._moveHue.call(self, e); });
		return false;

	},
	_moveHue: function(e) {

		this._select.apply(this, [e, this
				.fields
				.eq(4)
				.val(parseInt(360*(150 - Math.max(0,Math.min(150,(e.pageY - this.currentHue.y))))/150, 10))
				.get(0)]);

		return false;

	},
	_upHue: function(e) {
		$(document).unbind('mouseup.cpSlider');
		$(document).unbind('mousemove.cpSlider');
		return false;
	},
	_downSelector: function(e) {

		var self = this;
		this.currentSelector = {
			pos: this.picker.find('.rich-color-picker-color').offset()
		};

		this._select.apply(this, [e, this
				.fields
				.eq(6)
				.val(parseInt(100*(150 - Math.max(0,Math.min(150,(e.pageY - this.currentSelector.pos.top))))/150, 10))
				.end()
				.eq(5)
				.val(parseInt(100*(Math.max(0,Math.min(150,(e.pageX - this.currentSelector.pos.left))))/150, 10))
				.get(0)
		]);
		$(document).bind('mouseup.cpSlider', function(e) { return self._upSelector.call(self, e); });
		$(document).bind('mousemove.cpSlider', function(e) { return self._moveSelector.call(self, e); });
		return false;

	},
	_moveSelector: function(e) {

		this._select.apply(this, [e, this
				.fields
				.eq(6)
				.val(parseInt(100*(150 - Math.max(0,Math.min(150,(e.pageY - this.currentSelector.pos.top))))/150, 10))
				.end()
				.eq(5)
				.val(parseInt(100*(Math.max(0,Math.min(150,(e.pageX - this.currentSelector.pos.left))))/150, 10))
				.get(0)
		]);
		return false;

	},
	_upSelector: function(e) {
		$(document).unbind('mouseup.cpSlider');
		$(document).unbind('mousemove.cpSlider');
		return false;
	},
	_enterSubmit: function(e) {
		this.picker.find('.rich-color-picker-submit').addClass('rich-color-picker-focus');
	},
	_leaveSubmit: function(e) {
		this.picker.find('.rich-color-picker-submit').removeClass('rich-color-picker-focus');
	},
	_enterCancel: function(e) {
		this.picker.find('.rich-color-picker-cancel').addClass('rich-color-picker-focus');
	},
	_leaveCancel: function(e) {
		this.picker.find('.rich-color-picker-cancel').removeClass('rich-color-picker-focus');
	},
	_clickSubmit: function(e) {

		var col = this.color;
		this._setCurrentColor(col);
		this._setIconColor(col);
		
		var rgb = col.toRGB();
		var newValue;
		
		if (this.options.rgbColorMode) {
			newValue = 'rgb('+rgb.r+', '+rgb.g+', '+rgb.b+')';
		} else {
			newValue = '#' + this._RGBToHex(rgb);
		}
		
		var input = $('input', this.element);
		if (newValue !== input.val()) {
			input.val(newValue);
			this._trigger("change", e, this._createEventArgument(col));
		}
		
		this.picker.hide();
		$(document).unbind('mousedown.colorPicker');
		return false;
	},
	_clickCancel: function(e) {
		this.picker.hide();
		$(document).unbind('mousedown.colorPicker');
		return false;
	},
	_show: function(e) {

		if (this._trigger("beforeshow", e, this._createEventArgument(this.color)) != false) {
			var top = 0;
			var left = 0;

			this.picker.css('visibility', 'hidden').css('display', 'block');
			
			var input = $('input', this.element);
			var inputOffsets = input.offset();
			var elementRect = {left:inputOffsets.left, top: inputOffsets.top, width: this.element.width(), height:input.outerHeight()};
			Richfaces.jQuery.position(elementRect, this.picker);		
			
			this.picker.css('visibility', 'visible');
			
			this._trigger("show", e, this._createEventArgument(this.color));
			this.picker.show();
			
			var self = this;
			$(document).bind('mousedown.colorPicker', function(e) { return self._hide.call(self, e); });
		}

		return false;

	},

	_hide: function(e) {

		if (!this._isChildOf(this.picker[0], e.target, this.picker[0])) {
			if (this._trigger("hide", e, this._createEventArgument(this.color)) != false) {
				this.picker.hide();
			}
			$(document).unbind('mousedown.colorPicker');
		}

	},
	_isChildOf: function(parentEl, el, container) {
		if (parentEl == el) {
			return true;
		}
		if (parentEl.contains) {
			return parentEl.contains(el);
		}
		if ( parentEl.compareDocumentPosition ) {
			return !!(parentEl.compareDocumentPosition(el) & 16);
		}
		var prEl = el.parentNode;
		while(prEl && prEl != container) {
			if (prEl == parentEl)
				return true;
			prEl = prEl.parentNode;
		}
		return false;
	},

	_RGBToHex: function(rgb) {
		var hex = [
			rgb.r.toString(16),
			rgb.g.toString(16),
			rgb.b.toString(16)
		];
		$.each(hex, function (nr, val) {
			if (val.length == 1) {
				hex[nr] = '0' + val;
			}
		});
		return hex.join('');
	},
	setColor: function(col) {
		if (typeof col == 'string') {
			col = this._fixColors(col, 'hex');
		} else if (col.r != undefined && col.g != undefined && col.b != undefined) {
			col = new RGBColor([col.r, col.g, col.b]);
		} else if (col.h != undefined && col.s != undefined && col.b != undefined) {
			col = new HSBColor([col.h, col.s, col.b]);
		} else {
			return this;
		}

		this.color = col;
		this._fillRGBFields(col);
		this._fillHSBFields(col);
		this._fillHexFields(col);
		this._setHue(col);
		this._setSelector(col);
		this._setCurrentColor(col);
		this._setIconColor(col);
		this._setNewColor(col);

	}

});
})(jQuery);
