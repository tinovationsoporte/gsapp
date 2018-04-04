/**
 * @namespace The PrimeFaces Extensions root namespace.
 */
PrimeFacesExt = {
    /**
     * Gets the extension of the current request URL.
     *
     * @author Thomas Andraschko
     * @returns {string} The URL extensions.
     */
	getRequestUrlExtension : function() {
		return PrimeFacesExt.getUrlExtension(location.href);
	},	
		
    /**
     * Gets the extension of the given URL.
     *
     * @author Thomas Andraschko
     * @returns {string} The URL extensions.
     */
	getUrlExtension : function(url) {
		return (url = url.substr(1 + url.lastIndexOf("/")).split('?')[0]).substr(url.lastIndexOf("."));
	},

    /**
     * Builds a resource URL for given parameters.
     * 
     * @author Thomas Andraschko
     * @param {string} name The name of the resource. For example: /core/core.js
     * @param {string} library The library of the resource. For example: primefaces-extensions
     * @param {string} version The version of the library. For example: 0.2.0-SNAPSHOT
     * @returns {string} The resource URL.
     */
	getFacesResource : function(name, library, version) {
		var scriptURI = PrimeFacesExt.getPrimeFacesExtensionsScriptURI();
        
		scriptURI = scriptURI.replace('/primefaces-extensions.js', name);
		
		if (PrimeFacesExt.useUncompressedResources) {
			scriptURI = scriptURI.replace('ln=' + PrimeFacesExt.RESOURCE_LIBRARY_UNCOMPRESSED, 'ln=' + library);
		} else {
			scriptURI = scriptURI.replace('ln=' + PrimeFacesExt.RESOURCE_LIBRARY, 'ln=' + library);
		}

		var extractedVersion = RegExp('[?&]v=([^&]*)').exec(scriptURI)[1];
		if (version) {
			scriptURI = scriptURI.replace('v=' + extractedVersion, 'v=' + version);
		} else {
			scriptURI = scriptURI.replace('v=' + extractedVersion, '');
		}

        return location.protocol + '//' + location.host + scriptURI;
	},

    /**
     * Gets the version of the current PrimeFaces Extensions library.
     *
     * @author Thomas Andraschko
     * @returns {string} The PrimeFaces Extensions version.
     */
	getPrimeFacesExtensionsVersion : function() {
		if (!PrimeFacesExt.VERSION) {
			var scriptURI = PrimeFacesExt.getPrimeFacesExtensionsScriptURI();
			PrimeFacesExt.VERSION = RegExp('[?&]v=([^&]*)').exec(scriptURI)[1];
		}

		return PrimeFacesExt.VERSION;
	},

    /**
     * Builds a resource URL for a PrimeFaces Extensions resource.
     *
     * @author Thomas Andraschko
     * @returns {string} The resource URL.
     */
	getPrimeFacesExtensionsResource : function(name) {
		var resourceLibrary = PrimeFacesExt.RESOURCE_LIBRARY;
		if (PrimeFacesExt.useUncompressedResources) {
			resourceLibrary = PrimeFacesExt.RESOURCE_LIBRARY_UNCOMPRESSED;
		}

		return PrimeFacesExt.getFacesResource(
				name,
				resourceLibrary,
				PrimeFacesExt.getPrimeFacesExtensionsVersion());
	},

    /**
     * Builds a resource URL for a PrimeFaces Extensions Compressed resource.
     * 
     * @author Thomas Andraschko
     * @param {string} name The name of the resource. For example: /core/core.js
     * @returns {string} The resource URL.
     */
	getPrimeFacesExtensionsCompressedResource : function(name) {
		return PrimeFacesExt.getFacesResource(
				name,
				PrimeFacesExt.RESOURCE_LIBRARY,
				PrimeFacesExt.getPrimeFacesExtensionsVersion());
	},

    /**
     * Checks if the FacesServlet is mapped with extension mapping. For example: .jsf/.xhtml.
     *
     * @author Thomas Andraschko
     * @returns {boolean} If mapped with extension mapping.
     */
	isExtensionMapping : function() {
		if (!PrimeFacesExt.IS_EXTENSION_MAPPING) {
			var scriptURI = PrimeFacesExt.getPrimeFacesExtensionsScriptURI();
			var primeFacesExtensionsScript = 'primefaces-extensions.js';

			PrimeFacesExt.IS_EXTENSION_MAPPING = scriptURI.charAt(scriptURI.indexOf(primeFacesExtensionsScript) + primeFacesExtensionsScript.length) === '.';
		}

		return PrimeFacesExt.IS_EXTENSION_MAPPING;
	},

    /**
     * Gets the URL extensions of current included resources. For example: jsf/xhtml.
     * This should only be used if extensions mapping is used.
     *
     * @author Thomas Andraschko
     * @returns {string} The URL extension.
     */
	getResourceUrlExtension : function() {
		if (!PrimeFacesExt.RESOURCE_URL_EXTENSION) {
			var scriptURI = PrimeFacesExt.getPrimeFacesExtensionsScriptURI();
			var primeFacesExtensionsScript = 'primefaces-extensions.js';
		
			PrimeFacesExt.RESOURCE_URL_EXTENSION = RegExp('primefaces-extensions.js.([^?]*)').exec(scriptURI)[1];
		}

		return PrimeFacesExt.RESOURCE_URL_EXTENSION;
	},
	
    /**
     * Checks if the current included scripts are uncompressed.
     *
     * @author Thomas Andraschko
     * @returns {boolean} If uncompresed resources are used.
     */
	useUncompressedResources : function() {
		if (!PrimeFacesExt.USE_UNCOMPRESSED_RESOURCES) {
			var scriptURI = PrimeFacesExt.getPrimeFacesExtensionsScriptURI();

			PrimeFacesExt.USE_UNCOMPRESSED_RESOURCES = scriptURI.indexOf(RESOURCE_LIBRARY_UNCOMPRESSED) !== -1;
		}

		return PrimeFacesExt.USE_UNCOMPRESSED_RESOURCES;
	},

    /**
     * Gets the resource URI of the current included primefaces-extensions.js.
     *
     * @author Thomas Andraschko
     * @returns {string} The resource URI.
     */
	getPrimeFacesExtensionsScriptURI : function() {
		if (!PrimeFacesExt.SCRIPT_URI) {
			PrimeFacesExt.SCRIPT_URI = $('script[src*="' + PrimeFacesExt.RESOURCE_IDENTIFIER + '/primefaces-extensions.js"]').attr('src');
		}

		return PrimeFacesExt.SCRIPT_URI;
	},

	/**
	 * Creates a widget and load the required resources if not already available.
	 * The .js and .css must has the same name as the widget and must be placed inside a directory with also the name.
	 * The file and directory names must be completely in lower case.
	 * For example: /imageareaselect/imageareaselect.js.
	 * 
	 * @author Thomas Andraschko
	 * @param {string} widgetName The name of the widget. For example: ImageAreaSelect.
	 * @param {object} widgetVar The variable in the window object for accessing the widget.
	 * @param {object} cfg An object with options.
	 * @param {boolean} hasStyleSheet If the css file should be loaded as well.
	 */
	cw : function(widgetName, widgetVar, cfg, hasStyleSheet) {
	    PrimeFacesExt.createWidget(widgetName, widgetVar, cfg, hasStyleSheet);
	},

	/**
	 * Creates a widget and load the required resources if not already available.
	 * The .js and .css must has the same name as the widget and must be placed inside a directory with also the name.
	 * The file and directory names must be completely in lower case.
	 * For example: /imageareaselect/imageareaselect.js.
	 * 
	 * @author Thomas Andraschko
	 * @param {string} widgetName The name of the widget. For example: ImageAreaSelect.
	 * @param {object} widgetVar The variable in the window object for accessing the widget.
	 * @param {object} cfg An object with options.
	 * @param {boolean} hasStyleSheet If the css file should be loaded as well.
	 */
	createWidget : function(widgetName, widgetVar, cfg, hasStyleSheet) {            
	    if (PrimeFacesExt.widget[widgetName]) {
	    	PrimeFacesExt.initWidget(widgetName, widgetVar, cfg);
	    } else {
	    	if (hasStyleSheet === true) {
	    		var styleSheet =
	    			PrimeFacesExt.getPrimeFacesExtensionsResource('/' + widgetName.toLowerCase() + '/' + widgetName.toLowerCase() + '.css');

	    		//insert style sheet after last style sheet with ln=primefaces
	    		if ($.browser.msie) {
	    			var indexToInsert;
	    			for (var i = 0; i < document.styleSheets.length; i++ ) {
	    				var currentStyleSheetURL = document.styleSheets[i].href.toString();
	    			    if (currentStyleSheetURL.indexOf('ln=primefaces') !== -1) {
	    			    	//add +1 to insert after this style sheet
	    			    	indexToInsert = i + 1;
	    			    }
	    			}

	    			if (indexToInsert) {
	    				document.createStyleSheet(styleSheet, indexToInsert);
	    			} else {
	    				PrimeFaces.error('No style sheet from PrimeFaces or PrimeFaces Extensions included. StyleSheet for PrimeFaces Extensions Widget ' + widgetName + ' will not be added.');
	    			}
	    		} else {
		    		var lastStyleSheet = $('link[href*="ln=primefaces"]:last');

		    		if (lastStyleSheet.length > 0) {
		    			lastStyleSheet.after('<link type="text/css" rel="stylesheet" href="' + styleSheet + '" />');
		    		} else {
		    			PrimeFaces.error('No style sheet from PrimeFaces or PrimeFaces Extensions included. StyleSheet for PrimeFaces Extensions Widget ' + widgetName + ' will not be added.');
		    		}
	    		}
	    	}

    		var script =
    			PrimeFacesExt.getPrimeFacesExtensionsResource('/' + widgetName.toLowerCase() + '/' + widgetName.toLowerCase() + '.js');

    		//load script
	        PrimeFaces.getScript(script, function() {
	        	PrimeFacesExt.initWidget(widgetName, widgetVar, cfg);
	        });
	    }
	},

	/**
	 * Creates the widget or calls "refresh" if already available.
	 * 
	 * @author Thomas Andraschko
	 * @param {string} widgetName The name of the widget. For example: ImageAreaSelect.
	 * @param {string} widgetVar The variable in the window object for accessing the widget.
	 * @param {object} cfg An object with options.
	 */
	initWidget : function(widgetName, widgetVar, cfg) {
		if (PrimeFaces.widgets[widgetVar]) {
			PrimeFaces.widgets[widgetVar].refresh(cfg);
		} else {
			PrimeFaces.widgets[widgetVar] = new PrimeFacesExt.widget[widgetName](cfg);
		}
	},

	/**
	 * Configures component specific localized text by given widget name and locale in configuration object.
	 * 
	 * @author Oleg Varaksin
	 * @param {string} widgetName The name of the widget. For example: 'TimePicker'.
	 * @param {object} cfg Configuration object as key, value pair. This object should keep current locale in cfg.locale.
     * @returns {object} cfg Configuration object with updated localized text (if any text to given locale were found). 
	 */    
    configureLocale : function(widgetName, cfg) {
        if (PrimeFacesExt.locales && PrimeFacesExt.locales[widgetName] && cfg.locale) {
            var localeSettings = PrimeFacesExt.locales[widgetName][cfg.locale];
            if(localeSettings) {
                for(var setting in localeSettings) {
                    if (localeSettings.hasOwnProperty(setting)) {
                        cfg[setting] = localeSettings[setting];
                    }
                }
            }
        }
        
        return cfg;
    },

	/**
	 * The JSF resource identifier.
	 * 
	 * @author Thomas Andraschko
	 * @type {string}
	 * @constant
	 */
	RESOURCE_IDENTIFIER : '/javax.faces.resource',

	/**
	 * The name of the PrimeFaces Extensions resource library.
	 *
	 * @author Thomas Andraschko
	 * @type {string}
	 * @constant
	 */
	RESOURCE_LIBRARY : 'primefaces-extensions',

	/**
	 * The name of the PrimeFaces Extensions Uncompressed resource library.
	 *
	 * @author Thomas Andraschko
	 * @type {string}
	 * @constant
	 */
	RESOURCE_LIBRARY_UNCOMPRESSED : 'primefaces-extensions-uncompressed'
};

/**
 * @namespace Namespace for behaviors.
 */
PrimeFacesExt.behavior = {};

/**
 * @namespace Namespace for widgets.
 */
PrimeFacesExt.widget = {};

/**
 * @namespace Namespace for localization.
 */
PrimeFacesExt.locales = {};

/**
 * @namespace Namespaces for components with localized text.
 */
PrimeFacesExt.locales.TimePicker = {};
PrimeFacesExt.locales.Timeline = {};

/**
 * JavaScript behavior.
 * 
 * @author Thomas Andraschko
 * @constructor
 */
PrimeFacesExt.behavior.Javascript = function(cfg, ext) {
	return cfg.execute.call(this, cfg.source, cfg.event, cfg.params, ext);
};
;PrimeFacesExt.getAjaxErrorHandlerInstance = function() {
	if (!PrimeFacesExt.AJAX_ERROR_HANDLER_INSTANCE) {
		var instance = new PrimeFacesExt.widget.AjaxErrorHandler();

		//  INIT IS CALLED AUTOMATICALLY ... instance.init();

		PrimeFacesExt.AJAX_ERROR_HANDLER_INSTANCE = instance;
	}

	return PrimeFacesExt.AJAX_ERROR_HANDLER_INSTANCE;
}

/**
 * PrimeFaces Extensions AjaxErrorHandler.
 *
 * @author Pavol Slany
 */
PrimeFacesExt.widget.AjaxErrorHandler = PrimeFaces.widget.BaseWidget.extend({

	DEFAULT_HOSTNAME : '???unknown???',

	init : function() {
		this.popupWindow = null;
		this.popupWindowRoot = null;
		this.popupWindowMask = null;

		this.hostname = this.DEFAULT_HOSTNAME;

		this.defaultSettings = {
			'title': '{error-name}',
			'body': '{error-message}',
			'button': 'Reload',
			'buttonOnclick': function() {
				document.location.href = document.location.href;
			},
			'onerror': function(error, response) {

			}
		};
		this.settings = this.defaultSettings;
		this.otherSettings = {};

		this.overwritePrimeFacesAjaxResponse();
	},

	overwritePrimeFacesAjaxResponse : function() {
		var _self = this;
		if (_self.isOveritedAjaxResponse) return;
		_self.isOveritedAjaxResponse = true;


		_self.isOveritedAjaxResponse = true;

		var handlerFunction = function() {
			try {
				var docPartialUpdate = arguments[3];
				if (!docPartialUpdate && arguments[1] && arguments[1].responseXML)
					docPartialUpdate = arguments[1].responseXML;

				var nodeErrors = null;
				if (docPartialUpdate)
					nodeErrors = docPartialUpdate.getElementsByTagName('error');

				if (nodeErrors && nodeErrors.length && nodeErrors[0].childNodes && nodeErrors[0].childNodes.length) {
					// XML => JSON
					var error = {};

					for (var i=0; i < nodeErrors[0].childNodes.length; i++) {
						var node = nodeErrors[0].childNodes[i];
						var key = node.nodeName;
						var val = node.nodeValue;

						if (node.childNodes && node.childNodes.length) {
							val = node.childNodes[0].nodeValue;
						}

						error[key] = val;
					}

					if (error['error-name']) {
						// findErrorSettings
						var errorSetting = _self.findErrorSettings(error['error-name']);

						//skip dialog if onerror is defined and returns false
						if (errorSetting['onerror']) {
							var onerrorFunction = errorSetting['onerror'];
							if (onerrorFunction.call(this, error, arguments[2]) === false) {
								return true;
							}
						}

						// Copy updates to errorSettings ...
						if (error.updateCustomContent && error.updateCustomContent.substring(-13) == '<exception />') {
							error.updateCustomContent = null;
						}

						if (error.updateTitle && error.updateTitle.substring(-13) == '<exception />') {
							error.updateTitle = null;
						}

						if (error.updateBody && error.updateBody.substring(-13) == '<exception />') {
							error.updateBody = null;
						}

						if (error.updateViewState && error.updateViewState.substring(-13) == '<exception />') {
							error.updateViewState = null;
						}

						errorSetting.updateCustomContent = error.updateCustomContent;
						errorSetting.updateTitle = error.updateTitle;
						errorSetting.updateBody = error.updateBody;
						errorSetting.updateViewState = error.updateViewState;

						var errorData = _self.replaceVariables(errorSetting, error);

						_self.show(errorData);

						return true;
					}
				}
			}
			catch (e) {
				try {
					console.error('Unknown response in AjaxExceptionHandler:', e);
				} catch (ee) {}
			}
		};

		$(document).ajaxComplete(handlerFunction);
		$(document).on('pfAjaxComplete.ajaxerrorhandler', handlerFunction);
	},

	isVisible : function() {
		return this.popupWindow && this.popupWindow.isVisible();
	},

	hide : function () {
		if (this.dialogWidget) {
			this.dialogWidget.hide();
			this.dialogWidget = null;

			this.dialog.remove();
		}
	},

	show : function(errorData) {
		this.hide();

		//create required html
		this.dialog = $('<div id="ajaxErrorHandlerDialog" class="ui-dialog ui-widget ui-widget-content ui-overlay-hidden ui-corner-all ui-shadow pe-ajax-error-handler" style="width: auto; height: auto;"></div>');

		//append to DOM
		$('body').append(this.dialog);

		//custom content?
		if (errorData.updateCustomContent) {
			this.dialog.append(errorData.updateCustomContent);
		} else {
			//create required html
			var dialogHeader = $('<div class="ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-top"></div>');
			var dialogHeaderText = $('<span class="ui-dialog-title"></span>');
			var dialogContent = $('<div class="ui-dialog-content ui-widget-content" style="height: auto;"></div>');
			var dialogButtonPane = $('<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix"></div>')

			//append to DOM
			dialogHeader.append(dialogHeaderText);

			this.dialog.append(dialogHeader);
			this.dialog.append(dialogContent);
			this.dialog.append(dialogButtonPane);

			//setup button
			var dialogButton = $('<button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><span class="ui-button-text">' + errorData['button'] + '</span></button>');
			var buttonOnclickFunction = errorData['buttonOnclick'];
			dialogButton.click(function() {
				buttonOnclickFunction.call(this);
			});

			dialogButtonPane.append(dialogButton);

			//add title
			if (errorData.updateTitle) {
				dialogHeaderText.append(errorData.updateTitle);
			} else {
				dialogHeaderText.append(errorData.title);
			}

			//add body
			if (errorData.updateBody) {
				dialogContent.append(errorData.updateBody);
			} else {
				dialogContent.append(errorData.body);
			}
		}

		//create Dialog widget
		this.dialogWidget = new PrimeFaces.widget.Dialog({
			id : 'ajaxErrorHandlerDialog',
			resizable : true,
			draggable : true,
			modal : true
		});

		this.dialogWidget.show();
	},

	getDefaultErrorTime : function() {
		return new Date().toString();
	},

	findErrorSettings : function(name) {
		if (!name) {
			return jQuery.extend({}, this.settings);
		}

		if (!this.otherSettings[name]) {
			jQuery.extend({}, this.settings);
		}

		return jQuery.extend({}, this.settings, this.otherSettings[name]);
	},

	addErrorSettings : function(newSettings) {
		if (!newSettings) {
			return;
		}

		if (!newSettings.type) {
			this.settings = jQuery.extend({}, this.settings, newSettings);
			return;
		}

		var type = this.otherSettings[newSettings.type] || {};

		this.otherSettings[newSettings.type] = jQuery.extend({}, type, newSettings);
	},

	replaceAll : function(str, key, val) {
		var newStr;

		while ((newStr = str.replace(key, val)) != str) {
			str = newStr
		};

		return str;
	},

	replaceVariables : function(obj, variables) {
		if (!obj) {
			return text;
		}

		variables = jQuery.extend({
			'error-hostname': this.hostname,
			'error-stacktrace': '',
			'error-time': this.getDefaultErrorTime()
		}, variables);

		var returnValue = {};

		jQuery.each(obj, $.proxy(function(key, val) {
			if (typeof(val) == 'string') {
				jQuery.each(variables, $.proxy(function(iVar, iVal) {
					val = this.replaceAll(val, '{' + iVar + '}', iVal.replace(/\n/g, '<br />'));
				}, this));
			}

			returnValue[key] = val;
		}, this));

		return returnValue;
	},

	setHostname : function(hostname) {
		this.hostname = hostname;
	}
});
;/**
 * PrimeFaces Extensions Spotlight Widget.
 *
 * @author Pavol Slany
 */
PrimeFacesExt.widget.Spotlight = PrimeFaces.widget.BaseWidget.extend({

	/**
	 * Initializes the widget.
	 *
	 * @param {object} cfg The widget configuration.
	 */
	init : function(cfg) {
		this.id = cfg.id;
		this.blocked = cfg.blocked;
		this.content = $(PrimeFaces.escapeClientId(this.id));

		PrimeFacesExt.widget.Spotlight.cache = PrimeFacesExt.widget.Spotlight.cache || {};

		////////////////////////
		// Mask
		this.getMask = function () {
			var mask = PrimeFacesExt.widget.Spotlight.cache['PrimeFacesExt.widget.Spotlight.MaskAround:' + this.id];
			if (!mask) {
				mask = new PrimeFacesExt.widget.Spotlight.MaskAround(this.id);
				PrimeFacesExt.widget.Spotlight.cache['PrimeFacesExt.widget.Spotlight.MaskAround:' + this.id] = mask;
			}
			return (this.getMask = function () {
				return mask
			})();
		}

		this.block = function () {
			this.blocked = true;
			// Show MASK
			this.getMask().show();

			// focus area ...
			//this.getFocusArea().activate();
			this.enableModality();
		}
		this.unblock = function () {
			this.blocked = false;
			// Hide MASK
			this.getMask().hide();

			// focus area ...
//			this.getFocusArea().deactivate()
			this.disableModality();
		}

		if (this.blocked) {
			this.block();
		}
		else {
			this.unblock();
		}

		this.removeScriptElement(this.id);
	},

	enableModality: function() {

		//disable tabbing out of modal dialog and stop events from targets outside of dialog
		this.disableModality();

		this.content.bind('keydown.lightspot', function(event) {
			if(event.keyCode !== $.ui.keyCode.TAB) {
				return;
			}

			var tabbables = $(':tabbable', this),
				first = tabbables.filter(':first'),
				last  = tabbables.filter(':last');

			if (event.target === last[0] && !event.shiftKey) {
				first.focus(1);
				return false;
			} else if (event.target === first[0] && event.shiftKey) {
				last.focus(1);
				return false;
			}
		});

		// if focus is out of panel => set focus ...
		var focused = $(':focus', this.content);
		if (!focused || !focused.length)
			$(':tabbable', this.content).filter(':first').focus(1);

	},

	disableModality: function(){
		this.content.unbind('.lightspot');
	},

	block:function () {
		this.block();
	},

	unblock:function () {
		this.unblock();
	}
});

/**
 * @author Pavol Slany
 */
PrimeFacesExt.widget.Spotlight.MaskAround = function (elementId) {
	var maskId = elementId + '_maskAround';

	var destinationOpacity = function () {
		var el = $('<div class="ui-widget-overlay"></div>');
		$('body').append(el);
		var opacity = el.css('opacity');
		el.remove();
		return opacity;
	}();

	var ElementPieceOfMask = function (maskKey, zIndex) {
		var idEl = maskId + maskKey;
		var zIndex = zIndex;
		var elementMustBeVisible = $(PrimeFaces.escapeClientId(idEl)).is(':visible');
		var getMaskElement = function () {
			var maskElement = $(PrimeFaces.escapeClientId(idEl));

			if (!maskElement || !maskElement.length) {
				maskElement = $('<div id="' + idEl + '" />');
				maskElement.css({
//					position: 'fixed',
					position: !$.browser.webkit ? 'fixed' : 'absolute',
					top:0,
					left:0,
					display:'none',
					zIndex:zIndex,
					overflow:'hidden',
					border: 'none'
				});
				maskElement.append($('<div class="ui-widget-overlay" style="position:absolute;"></div>').css('opacity', 1));
				$('body').append(maskElement);

				maskElement.click(function() {
					var content = $(PrimeFaces.escapeClientId(elementId));
					var tabbables = $(':tabbable', content);
					tabbables.filter(':first').focus(1);
				});
			}


			return maskElement;
		}
		var isMaskVisible = function () {
			var maskElement = $(PrimeFaces.escapeClientId(idEl));
			if (!maskElement || !maskElement.length) return false;
			return maskElement.is(":visible");
		}
		var isMaskInBody = function () {
			var maskElement = $(PrimeFaces.escapeClientId(idEl));
			if (!maskElement || !maskElement.length) return false;
			return true;
		}

		var updateVisibility = function () {
			if (elementMustBeVisible) {
				if (!isMaskVisible()) {
					var el = getMaskElement();
					el.css('zIndex', zIndex);
					el.fadeTo("fast", destinationOpacity, updateVisibility);

				}
				return;
			}
			// ...
			if (isMaskVisible()) {
				getMaskElement().fadeOut('fast', function () {
					updateVisibility();
				});
				return;
			}

			if (isMaskInBody()) {
				getMaskElement().remove();
				return;
			}
		}

		return {
			updatePosition:function (x0, y0, x1, y1) {
				// X,Y correction
				if (getMaskElement().css('position')=='fixed') {
					var xx = $(window).scrollLeft();
					var yy = $(window).scrollTop();
					x0 -= xx;
					x1 -= xx;
					y0 -= yy;
					y1 -= yy;
				}

				$('<div class="ui-widget-overlay"></div>')
				var el = getMaskElement();
				el.css({
					left:x0,
					top:y0,
					width:(x1 - x0),
					height:(y1 - y0)
				});

				var maxSize = getMaxSize();
				$(el.children()[0]).css({
					left:(0 - x0),
					top:(0 - y0),
					height:maxSize.height,
					width:maxSize.width
				});
			},
			show:function (zIndexNew) {
				elementMustBeVisible = true;
				zIndex = zIndexNew;
				updateVisibility();
			},
			hide:function () {
				elementMustBeVisible = false;
				updateVisibility();
			}
		};
	};

	var zIndex = ++PrimeFaces.zindex;

	var top = new ElementPieceOfMask('_top', zIndex);
	var left = new ElementPieceOfMask('_left', zIndex);
	var bottom = new ElementPieceOfMask('_bottom', zIndex);
	var right = new ElementPieceOfMask('_right', zIndex);

	var getMaxSize = function () {
		var winWidth = $(window).width();
		var winHeight = $(window).height();
		var docWidth = $(document).width();
		var docHeight = $(document).height();

		var maxWidth = winWidth > docWidth ? winWidth : docWidth;
		var maxHeight = winHeight > docHeight ? winHeight : docHeight;

		return {
			width:maxWidth,
			height:maxHeight
		};
	}

	// check IE8 browser (it works in all BROWSER MODEs and DOCUMENT MODEs)
	var isIE8 = function () {
		if ($.browser.msie) {
			// document.documentMode is since IE8
			// window.performance is since IE9
			if (document.documentMode && !window.performance) return true;
		}
		return false;
	}


	var updateMaskPositions = function () {
		var maxSize = getMaxSize();

		var maxWidth = maxSize.width;
		var maxHeight = maxSize.height;

		// Check PANEL position for MASK
		var el = $(PrimeFaces.escapeClientId(elementId));
		var x0 = el.offset().left;
		var y0 = el.offset().top;
		var x1 = x0 + el.outerWidth();
		var y1 = y0 + el.outerHeight();

		// Correct MASK position before, any parents is with overflow=AUTO|HIDDEN|SCROLL
		var elParent = el.parent();
		while (elParent.length > 0 && elParent[0].tagName != 'HTML') {
			var overflow = elParent.css('overflow');

			if (overflow == 'auto' || overflow == 'hidden' || overflow == 'scroll') {
				// IE BUG - if height is 0 => CSS problem with overflow => ignore it
				if (elParent.height() > 0) {
					var offset = elParent.offset();
					if (x0 < offset.left) x0 = offset.left;
					if (y0 < offset.top) y0 = offset.top;
					if (x1 > offset.left + elParent.outerWidth()) x1 = offset.left + elParent.outerWidth();
					if (y1 > offset.top + elParent.outerHeight()) y1 = offset.top + elParent.outerHeight();
				}
			}

			elParent = elParent.parent();
		}


		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 < x0) x1 = x0;
		if (y1 < y0) y1 = y0;

		if (el.outerHeight() > 0 && y1 - y0 <= 5) {
			try {
				var elFocus = $(PrimeFaces.escapeClientId(elementId) + ' :focusable');
				// Change focus ...
				if (elFocus.length < 2) {
					// If ELEMENT does not exist => create TMP element
					var tmpEl = $('<a href="#"> </a>');
					el.append(tmpEl);
					tmpEl.focus();
					tmpEl.remove();
				}
				else {
					// ELEMENT exists
					$(elFocus[1]).focus();
				}
				// SET FOCUS for first element
				$(elFocus[0]).focus();

			} catch (e) {
			}
		}

		var ie8Corecting = 0;
		// IE8 has bug with layouts => check IE8 (it works in all BROWSER MODE and DOCUMENT MODE)
		if (isIE8()) {
			ie8Corecting = 1;
		}

//		var bodyOffset = {top: $(window).scrollTop(), left: $(window).scrollLeft()};
		var bodyOffset = {top:0, left:0};

		top.updatePosition(
			0 - bodyOffset.left,
			0 - bodyOffset.top,
			maxWidth - bodyOffset.left,
			y0 - bodyOffset.top);
		bottom.updatePosition(
			0 - bodyOffset.left,
			y1 - bodyOffset.top,
			maxWidth - bodyOffset.left,
			maxHeight - bodyOffset.top);
		left.updatePosition(
			0 - bodyOffset.left,
			y0 - bodyOffset.top + ie8Corecting,
			x0 - bodyOffset.left,
			y1 - bodyOffset.top - ie8Corecting);
		right.updatePosition(
			x1 - bodyOffset.left,
			y0 - bodyOffset.top + ie8Corecting,
			maxWidth - bodyOffset.left,
			y1 - bodyOffset.top - ie8Corecting);
	}

	var mustBeShowed = false;
	var resizeTimer = null;
	$(window).bind('resize', function () {
		if (resizeTimer) clearTimeout(resizeTimer);
		resizeTimer = setTimeout(updatePositions, 100);
	});

	var updatePositions = function () {
		if (mustBeShowed === true) {
			updateMaskPositions();
			setTimeout(updatePositions, 150);
		}
	};
	var showAreas = function () {
		mustBeShowed = true;

		updatePositions();

		var zIndex = ++PrimeFaces.zindex;

		top.show(zIndex);
		bottom.show(zIndex);
		left.show(zIndex);
		right.show(zIndex);
	}
	var hideAreas = function () {
		mustBeShowed = false;

		top.hide();
		bottom.hide();
		left.hide();
		right.hide();
	}

	return {
		show:function () {
			showAreas();
		},
		hide:function () {
			hideAreas();
		}
	};
};
;/**
 * Resolves the resources for the CKEditor.
 * 
 * @param {string} resource The requested resource from CKEditor.
 * @returns {string} The faces resource URL.
 */
CKEDITOR_GETURL = function(resource) {
	var facesResource;

	//do not resolve
	if (resource.indexOf('?resolve=false') !== -1) {
		facesResource = resource.replace('?resolve=false', '');
	} else {
		//already wrapped?
		var libraryVersionIndex = resource.indexOf('v=' + PrimeFacesExt.getPrimeFacesExtensionsVersion());
		if (libraryVersionIndex !== -1) {
			//look for appended resource
			var appendedResource = resource.substring(libraryVersionIndex + ('v=' + PrimeFacesExt.getPrimeFacesExtensionsVersion()).length);
	
			if (appendedResource.length > 0) {
				//remove append resource from url
				facesResource = resource.substring(0, resource.length - appendedResource.length);
	
				var resourceIdentiferPosition = facesResource.indexOf(PrimeFacesExt.RESOURCE_IDENTIFIER);
				
				if (PrimeFacesExt.isExtensionMapping()) {
					var extensionMappingPosition = facesResource.lastIndexOf('.' + PrimeFacesExt.getResourceUrlExtension());
	
					//extract resource
					var extractedResource = facesResource.substring(resourceIdentiferPosition + PrimeFacesExt.RESOURCE_IDENTIFIER.length, extensionMappingPosition);
	
					facesResource = PrimeFacesExt.getPrimeFacesExtensionsCompressedResource(extractedResource + appendedResource);
				} else {
					var questionMarkPosition = facesResource.indexOf('?');
	
					//extract resource
					var extractedResource = facesResource.substring(resourceIdentiferPosition + PrimeFacesExt.RESOURCE_IDENTIFIER.length, questionMarkPosition);
	
					facesResource = PrimeFacesExt.getPrimeFacesExtensionsCompressedResource(extractedResource + appendedResource);
				}
			} else {
				facesResource = resource;
			}
		} else {
			facesResource = PrimeFacesExt.getPrimeFacesExtensionsCompressedResource('/ckeditor/' + resource);
		}
	}
	
	return facesResource;
}

/**
 * PrimeFaces Extensions CKEditor Widget.
 * 
 * @author Thomas Andraschko
 */
PrimeFacesExt.widget.CKEditor = PrimeFaces.widget.DeferredWidget.extend({
	
	/**
	 * Initializes the widget.
	 * 
	 * @param {object} cfg The widget configuration.
	 */
	init : function(cfg) {
		this._super(cfg);

	    this.instance = null;
	
		this.options = {};
		//add widget to ckeditor config, this is required for the save event
		this.options.widgetVar = this.cfg.widgetVar;
	
		if (this.cfg.skin) {
			this.options.skin = this.cfg.skin;
		}
		if (this.cfg.width) {
			this.options.width = this.cfg.width;
		}
		if (this.cfg.height) {
			this.options.height = this.cfg.height;
		}
		if (this.cfg.theme) {
			this.options.theme = this.cfg.theme;
		}
		if (this.cfg.toolbar) {
			this.options.toolbar = this.cfg.toolbar;
		}
		if (this.cfg.readOnly) {
			this.options.readOnly = this.cfg.readOnly;
		}
		if (this.cfg.interfaceColor) {
			this.options.uiColor = this.cfg.interfaceColor;
		}
		if (this.cfg.language) {
			this.options.language = this.cfg.language;
		}
		if (this.cfg.defaultLanguage) {
			this.options.defaultLanguage = this.cfg.defaultLanguage;
		}
		if (this.cfg.contentsCss) {
			this.options.contentsCss = this.cfg.contentsCss;
		}
		if (this.cfg.customConfig) {
			this.options.customConfig = this.cfg.customConfig + "?resolve=false";
		}
	
		//check if ckeditor is already included
		if (!$.fn.ckeditor) {
			var ckEditorScriptURI =
				PrimeFacesExt.getPrimeFacesExtensionsCompressedResource('/ckeditor/ckeditor.js');
			
			var jQueryAdapterScriptURI =
				PrimeFacesExt.getPrimeFacesExtensionsCompressedResource('/ckeditor/adapters/jquery.js');		
	
			//load ckeditor
			PrimeFaces.getScript(ckEditorScriptURI, $.proxy(function(data, textStatus) {
	
				//load jquery adapter
				PrimeFaces.getScript(jQueryAdapterScriptURI, $.proxy(function(data, textStatus) {
	
					this.renderDeferred();

				}, this));
	
			}, this), true);
	
		} else {
			this.renderDeferred();
		}
	},

	/**
	 * Initializes the CKEditor instance.
	 * This method will be called when the resources for the CKEditor are loaded.
	 * 
	 * @private
	 */
	_render : function() {
		if (!this.instance) {
			//overwrite save button
			this.overwriteSaveButton();
	
			//remove old instances if required
			var oldInstance = CKEDITOR.instances[this.id];
			if (oldInstance) {
				try {
					oldInstance.destroy(true);
				} catch (err) {
					if (window.console && console.log) {
						console.log('CKEditor throwed a error while destroying the old instance: ' + err);
					}
				}
			}
	
			//initialize ckeditor after all resources were loaded
			this.jq.ckeditor($.proxy(function() { this.initialized(); }, this), this.options);
		}
	},

	/**
	 * Overwrites the save button.
	 *
	 * @private
	 */
	overwriteSaveButton : function() {
		//overwrite save button
		CKEDITOR.plugins.registered['save'] = {
			init : function(editor) {

				//get widget
				var widget = PF(editor.config.widgetVar);
				var command = editor.addCommand('save', {
					modes : { wysiwyg:1, source:1 },
					exec : function(editor) {
						if (widget.cfg.behaviors) {
							var saveCallback = widget.cfg.behaviors['save'];
						    if (saveCallback) {
						    	var ext = {
						    			params: []
						    	};
	
						    	saveCallback.call(widget, null, ext);
						    }
						}
					}
				});

				editor.ui.addButton('Save', {label : editor.lang.save, command : 'save', title : '' });
			}
		}
	},

	/**
	 * This method will be called when the CKEditor was initialized. 
	 *
	 * @private
	 */
	initialized : function() {
		//get instance
		this.instance = this.jq.ckeditorGet();
		
		//fire initialize event
		this.fireEvent('initialize');
		
		//register blur and focus event
	    this.instance.on('blur', $.proxy(function() { this.fireEvent('blur'); }, this));
	    this.instance.on('focus', $.proxy(function() { this.fireEvent('focus'); }, this));

	    //changes to WYSIWYG mode
    	this.instance.on('contentDom', $.proxy(function() {
    		this.fireEvent('wysiwygMode');
      	}, this));
 
    	//changes to source mode
    	this.instance.on('mode', $.proxy(function(event) {
			if (this.instance.mode != 'source') {
				return;    	
			}
			this.fireEvent('sourceMode');
    	}, this));
	},

	/**
	 * This method fires an event if the behavior was defined.
	 *
	 * @param {string} eventName The name of the event.
	 * @private
	 */
	fireEvent : function(eventName) {
		if (this.cfg.behaviors) {
			var callback = this.cfg.behaviors[eventName];
		    if (callback) {
		    	var ext = {
		    			params: []
		    	};
	
		    	callback.call(this, null, ext);
		    }
		}
	},

	/**
	 * Destroys the CKEditor instance.
	 */
	destroy : function() {
	    if (this.instance) {
	        this.instance.destroy(true);
	        this.instance = null;
	    }
	
	    this.jq.show();
	},

	/**
	 * Checks if the editor is in dirty state.
	 */
	isDirty : function() {
		if (!this.instance) {
			return false;
		}

		return this.instance.checkDirty();
	},

	/**
	 * Sets readOnly to the CKEditor.
	 */
	setReadOnly : function(readOnly) {
	    this.instance.setReadOnly(readOnly !== false);
	},

	/**
	 * Checks if the CKEditor is readOnly.
	 */
	isReadOnly : function() {
	    return this.instance.readOnly;
	},

	/**
	 * Indicates that the editor instance has focus.
	 */
	hasFocus : function() {
	    return this.instance.focusManager.hasFocus;
	},

	/**
	 * Returns the CKEditor instance.
	 */
	getEditorInstance : function() {
	    return this.instance;
	}
});
;/**
 * PrimeFaces Extensions DynaForm Widget.
 *
 * @author Oleg Varaksin
 */
PrimeFacesExt.widget.DynaForm = PrimeFaces.widget.BaseWidget.extend({

    /**
     * Initializes the widget.
     *
     * @param {object} cfg The widget configuration.
     */
    init : function(cfg) {
        this._super(cfg);
        if (!cfg.isPostback) {
            this.toggledExtended = false;
        }

        if (cfg.autoSubmit && !PF(cfg.widgetVar)) {
            this.submitForm();
        } else if (cfg.isPostback && this.toggledExtended && this.uuid == cfg.uuid) {
            var rows = this.jq.find("tr.pe-dynaform-extendedrow");
            if (rows.length > 0) {
                if (this.openExtended) {
                    rows.show();
                } else {
                    rows.hide();
                }
            }
        }
        
        this.uuid = cfg.uuid;
    },

    toggleExtended : function() {
        var rows = this.jq.find("tr.pe-dynaform-extendedrow");
        if (rows.length > 0) {
            rows.toggle();

            this.toggledExtended = true;
            this.openExtended = $(rows[0]).css("display") != "none";
        }
    },

    submitForm : function() {
        this.jq.find(":submit").trigger('click');
    }
});;/**
 * PrimeFaces Extensions ImageRotateAndResize Widget.
 *
 * @author Thomas Andraschko
 */
PrimeFacesExt.widget.ImageRotateAndResize = PrimeFaces.widget.BaseWidget.extend({
	
	/**
	 * Initializes the widget.
	 * 
	 * @param {object} cfg The widget configuration.
	 */
	init : function(cfg) {
		this.id = cfg.id;
		this.cfg = cfg;

		this.initialized = false;
		
		this.removeScriptElement(this.id);
	},

	/**
	 * Initializes the settings.
	 *
	 * @private
	 */
	initializeLazy : function() {
		if (!this.initialized) {
			this.target = PrimeFaces.Expressions.resolveComponentsAsSelector(this.cfg.target)[0];
			this.imageSrc = this.target.src;
			this.imageWidth = this.target.width;
			this.imageHeight = this.target.height;
			this.degree = 0;
			this.newImageWidth = this.target.width;
			this.newImageHeight = this.target.height;
			this.initialized = true;
		}
	},

	/**
	 * Reloads the widget.
	 */
	reload : function() {
		this.initialized = false;
		this.initializeLazy();
	},

	/**
	 * Rotates the image to the left direction.
	 * 
	 * @param {number} degree The degree.
	 */
	rotateLeft : function(degree) {
		this.initializeLazy();
	
		if ((this.degree - degree) <= 0) {
			this.degree = 360 - ((this.degree - degree) * -1);
		}
		else {
			this.degree -= degree;
		}
	
		this.redrawImage(false, true);
	},

	/**
	 * Rotates the image to the right direction.
	 * 
	 * @param {number} degree The degree.
	 */
	rotateRight : function(degree) {
		this.initializeLazy();
	
		if ((this.degree + degree) >= 360) {
			this.degree = (this.degree + degree) - 360;
		}
		else {
			this.degree += degree;
		}
	
		this.redrawImage(false, true);
	},

	/**
	 * Resizes the image to the given width and height.
	 * 
	 * @param {number} width The new width of the image.
	 * @param {number} height The new height of the image.
	 */
	resize : function(width, height) {
		this.initializeLazy();
	
		this.newImageWidth = width;
		this.newImageHeight = height;
	
		this.redrawImage(true, false);
	},

	/**
	 * Scales the image with the given factor.
	 * 
	 * @param {number} scaleFactor The scale factor. For example: 1.1 = scales the image to 110% size.
	 */
	scale : function(scaleFactor) {
		this.initializeLazy();
	
		this.newImageWidth = this.newImageWidth * scaleFactor;
		this.newImageHeight = this.newImageHeight * scaleFactor;
	
		this.redrawImage(true, false);
	},

	/**
	 * Restores the default image.
	 * It also fires the rotate and resize event with the default values.
	 */
	restoreDefaults : function() {
		this.initializeLazy();
	
		this.newImageWidth = this.imageWidth;
		this.newImageHeight = this.imageHeight;
		this.degree = 0;
	
		this.redrawImage(true, true);
	},

	/**
	 * Redraws the image.
	 * 
	 * @param {fireResizeEvent} fireResizeEvent If the resize event should be fired.
	 * @param {fireRotateEvent} fireRotateEvent If the rotate event should be fired.
	 * @private
	 */
	redrawImage : function(fireResizeEvent, fireRotateEvent) {

		var rotation;
		if (this.degree >= 0) {
			rotation = Math.PI * this.degree / 180;
		} else {
			rotation = Math.PI * (360 + this.degree) / 180;
		}
	
		var cos = Math.cos(rotation);
		var sin = Math.sin(rotation);
	
		//check for < IE9, otherwise use canvas
		if ($.browser.msie && parseInt($.browser.version) <= 8) {
			//create new image
			var image = document.createElement('img');

			image.onload = $.proxy(function() {
				//set new size
				image.height = this.newImageHeight;
				image.width = this.newImageWidth;
		
				//apply rotation for IE
				image.style.filter = "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (sin * -1) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')";	
				
				//replace old image with new generated one
				image.id = this.target.id;
				this.target.parentNode.replaceChild(image, this.target);
				this.target = image;
				
				if (fireResizeEvent) {
					this.fireResizeEvent();
				}
				if (fireRotateEvent) {
					this.fireRotateEvent();
				}
				
			}, this);
			
			image.src = this.imageSrc;
			
		} else {
			//create canvas instead of img
			var canvas = document.createElement('canvas');
	
			//new image with new size
			var newImage = new Image();

			newImage.onload = $.proxy(function() {
				//rotate
				canvas.style.width = canvas.width = Math.abs(cos * newImage.width) + Math.abs(sin * newImage.height);
				canvas.style.height = canvas.height = Math.abs(cos * newImage.height) + Math.abs(sin * newImage.width);
		
				var context = canvas.getContext('2d');
				context.save();
				
				if (rotation <= Math.PI/2) {
					context.translate(sin * newImage.height, 0);
				} else if (rotation <= Math.PI) {
					context.translate(canvas.width, (cos * -1) * newImage.height);
				} else if (rotation <= 1.5 * Math.PI) {
					context.translate((cos * -1) * newImage.width, canvas.height);
				} else {
					context.translate(0, (sin * -1) * newImage.width);
				}
		
				context.rotate(rotation);
				context.drawImage(newImage, 0, 0, newImage.width, newImage.height);
				context.restore();	
		
				//replace image with canvas and set src attribute
				canvas.id = this.target.id;
				canvas.src = this.target.src;
				this.target.parentNode.replaceChild(canvas, this.target);
				this.target = canvas;
				
				if (fireResizeEvent) {
					this.fireResizeEvent();
				}
				if (fireRotateEvent) {
					this.fireRotateEvent();
				}
				
			}, this);

			newImage.src = this.imageSrc;
			newImage.width = this.newImageWidth;
			newImage.height = this.newImageHeight;
		}
	},

	/**
	 * Fires the rotate event.
	 *
	 * @private
	 */
	fireRotateEvent : function() {
		if (this.cfg.behaviors) {
			var callback = this.cfg.behaviors['rotate'];
		    if (callback) {
		    	var ext = {
		    			params: [
		    			         { name: this.id + '_degree', value: this.degree }
		    			]
		    	};

		    	callback.call(this, null, ext);
		    }
		}
	},

	/**
	 * Fires the resize event.
	 *
	 * @private
	 */
	fireResizeEvent : function() {
		if (this.cfg.behaviors) {
			var callback = this.cfg.behaviors['resize'];
		    if (callback) {
		    	var ext = {
		    			params: [
		    			         { name: this.id + '_width', value: this.newImageWidth },
		    			         { name: this.id + '_height', value: this.newImageHeight }
		    			]
		    	};

		    	callback.call(this, null, ext);
		    }
		}
	}
});
;/**
 * PrimeFaces Extensions TriStateCheckbox Widget.
 *
 * @author Mauricio Fenoglio
 */
PrimeFacesExt.widget.TriStateCheckbox = PrimeFaces.widget.BaseWidget.extend({

    /**
     * Initializes the widget.
     *
     * @param {object} cfg The widget configuration.
     */
    init:function (cfg) {
        this._super(cfg);

        this.input = $(this.jqId + '_input');
        this.box = this.jq.find('.ui-chkbox-box');
        this.icon = this.box.children('.ui-chkbox-icon');
        this.itemLabel = this.jq.find('.ui-chkbox-label');
        this.disabled = this.input.is(':disabled');
        this.fixedMod = function(number,mod){
            return ((number%mod)+mod)%mod;
        }

        var _self = this;

        //bind events if not disabled
        if (!this.disabled) {
            this.box.mouseover(function () {
                _self.box.addClass('ui-state-hover');
            }).mouseout(function () {
                _self.box.removeClass('ui-state-hover');
            }).click(function (event) {
                _self.toggle(1);
                if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
            });

            //toggle state on label click
            this.itemLabel.click(function () {
                _self.toggle(1);
                if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
            });

            //adding accesibility
            this.box.bind('keydown', function(event) {                
                switch(event.keyCode){
                    case 38:
                        _self.toggle(1);
                        if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                        break;
                    case 40:
                        _self.toggle(-1);
                        if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                        break;
                    case 39:
                        _self.toggle(1);
                        if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                        break;
                    case 37:
                        _self.toggle(-1);
                        if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                        break;
                    case 32:
                        _self.toggle(1);
                        if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                        break; 
                }    
            });
            
            // client behaviors
            if (this.cfg.behaviors) {
                PrimeFaces.attachBehaviors(this.input, this.cfg.behaviors);
            }
        }

        // pfs metadata
        this.input.data(PrimeFaces.CLIENT_ID_DATA, this.id);
    },

    toggle:function (direction) {
        if (!this.disabled) {
            var oldValue = parseInt(this.input.val());
            var newValue = this.fixedMod((oldValue + direction),3);
            this.input.val(newValue);

            // remove / add def. icon and active classes
            if (newValue == 0) {
                this.box.removeClass('ui-state-active');
            } else {
                this.box.addClass('ui-state-active');
            }

            // remove old icon and add the new one
            var iconsClasses = this.box.data('iconstates');
            this.icon.removeClass(iconsClasses[oldValue]).addClass(iconsClasses[newValue]);
            
            // change title to the new one
            var iconTitles = this.box.data('titlestates');
            if(iconTitles!=null && iconTitles.length>0){
                this.box.attr('title', iconTitles[newValue]);
            }
           
            // fire change event
            this.input.change();
        }
    }
});
;/**
 * PrimeFaces Extensions TriStateManyCheckbox Widget.
 *
 * @author Mauricio Fenoglio
 */
PrimeFacesExt.widget.TriStateManyCheckbox = PrimeFaces.widget.BaseWidget.extend({

    /**
     * Initializes the widget.
     *
     * @param {object} cfg The widget configuration.
     */
    init:function (cfg) {
        this._super(cfg);

        this.outputs = this.jq.find('.ui-chkbox-box:not(.ui-state-disabled)');
        this.inputs = this.jq.find(':text:not(:disabled)');
        this.labels = this.jq.find('label:not(.ui-state-disabled)');
        this.fixedMod = function(number,mod){
            return ((number%mod)+mod)%mod;
        }
        var _self = this;

        this.outputs.mouseover(function () {
            $(this).addClass('ui-state-hover');
        }).mouseout(function () {
            $(this).removeClass('ui-state-hover');
        }).click(function (event) {
            _self.toggle($(this),1);
            if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
        });

        this.labels.click(function (event) {
            var element = $(this), input = $(PrimeFaces.escapeClientId(element.attr('for'))), checkbox = input.parent().next();
            checkbox.click();
            if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
        });

        //adding accesibility
        this.outputs.bind('keydown', function(event) {           
            switch(event.keyCode){
                case 38:
                    _self.toggle($(this),1);
                    if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                    break;
                case 40:
                    _self.toggle($(this),-1);
                    if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                    break;
                case 39:
                    _self.toggle($(this),1);
                    if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                    break;
                case 37:
                    _self.toggle($(this),-1);
                    if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                    break;
                case 32:
                    _self.toggle($(this),1);
                    if (event.preventDefault) { event.preventDefault(); } else { event.returnValue = false; } 
                    break; 
            }    
        });
        
        // client behaviors
        if (this.cfg.behaviors) {
            PrimeFaces.attachBehaviors(this.inputs, this.cfg.behaviors);
        }

        // pfs metadata
        this.inputs.data(PrimeFaces.CLIENT_ID_DATA, this.id);
    },

    toggle:function (checkbox,direction) {
        var inputField = checkbox.prev().find(':input');
        if (!checkbox.hasClass('ui-state-disabled')) {
            var oldValue = parseInt(inputField.val());
            var newValue = this.fixedMod((oldValue + direction),3);
            inputField.val(newValue);

            // remove / add def. icon and active classes
            if (newValue == 0) {
                checkbox.removeClass('ui-state-active');
            } else {
                checkbox.addClass('ui-state-active');
            }

            // remove old icon and add the new one
            var iconsClasses = checkbox.data('iconstates');
            checkbox.children().removeClass(iconsClasses[oldValue]).addClass(iconsClasses[newValue]);

            // change title to the new one
            var iconTitles = checkbox.data('titlestates');
            if(iconTitles!=null && iconTitles.length>0){
                checkbox.attr('title', iconTitles[newValue]);
            }

            // fire change event
            inputField.change();
        }
    }
});
