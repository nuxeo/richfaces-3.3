/**
 * $Id: editor_plugin_src.js 520 2008-01-07 16:30:32Z spocke $
 *
 * @author Moxiecode
 * @copyright Copyright © 2004-2008, Moxiecode Systems AB, All rights reserved.
 */

(function() {
	tinymce.PluginManager.requireLangPack('myemotions');
	tinymce.create('tinymce.plugins.MyEmotionsPlugin', {
		init : function(ed, url) {
			// Register commands
			ed.addCommand('mceMyEmotion', function() {
				ed.windowManager.open({
					file : url + '/myemotions.htm',
					width : 250 + parseInt(ed.getLang('myemotions.delta_width', 0)),
					height : 160 + parseInt(ed.getLang('myemotions.delta_height', 0)),
					inline : 1
				}, {
					plugin_url : url
				});
			});

			// Register buttons
			ed.addButton('myemotions', {title : 'myemotions.emotions_desc', cmd : 'mceMyEmotion', image : url + '/img/smiley-cool.gif'});
		},

		getInfo : function() {
			return {
				longname : 'MyEmotions',
				author : '',
				authorurl : '',
				infourl : '',
				version : tinymce.majorVersion + "." + tinymce.minorVersion
			};
		}
	});

	// Register plugin
	tinymce.PluginManager.add('myemotions', tinymce.plugins.MyEmotionsPlugin);
})();