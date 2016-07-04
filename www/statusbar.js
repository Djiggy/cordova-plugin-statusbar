function StatusbarTransparent () {
	this.transparentFlag = false;
};

StatusbarTransparent.prototype.getStatusbarHeight = function (success, fail) {
	cordova.exec(success ? success : null, fail ? fail : null, 'StatusbarTransparent', 'getStatusbarHeight', []);
};

module.exports = new StatusbarTransparent();