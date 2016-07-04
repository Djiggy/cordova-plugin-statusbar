function StatusbarTransparent () {
	this.transparentFlag = false;
};

StatusbarTransparent.prototype.getStatusbarHeight = function (success, fail) {
	cordova.exec(success ? success : null, fail ? fail : null, 'StatusbarTransparent', 'getStatusbarHeight', []);
};

StatusbarTransparent.prototype.isSupported = function (success, fail) {
	cordova.exec(success ? success : null, fail ? fail : null, 'StatusbarTransparent', 'isSupported', []);
};

module.exports = new StatusbarTransparent();