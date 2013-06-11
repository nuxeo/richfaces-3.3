if (!window.Richfaces) {
window.Richfaces = {};
}

Richfaces.processEffect = function(pm) {
	new Effect[pm.type]($(pm.targetId),pm);
};

Richfaces.effectEventOnOut = function(ename) {
	return ename.substr(0,2) == 'on' ? ename.substr(2) : ename;
};
