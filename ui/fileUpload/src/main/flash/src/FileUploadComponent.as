/**
 * FlashUploadComponent for Richfaces.ui.flashupload
 */
/**
 *
 */
import flash.external.*;
import flash.net.FileReference;
import flash.net.FileReferenceList;

// _root variables (from flashVars): 
// fileUploadId:String

class FileUploadComponent {
		
		private var fileDataList: Array;
		private var fileRefList: Array;
		private var acceptedTypes: Object; 
		private var noDuplicate: Boolean;
		private var maxFiles: Number; 
		private var fileTypes: Array;
		private var uploadIndex: Number;
		private var parent:MovieClip;
		private var disabled:Boolean;
	
		public function FileUploadComponent(parent:MovieClip) {
			this.parent = parent;
			this.fileDataList = new Array();
			this.fileRefList = new Array();
			this.acceptedTypes = new Object(); 
			this.noDuplicate = true;
			this.maxFiles = 5;
			this.fileTypes = null;
			this.uploadIndex = -1;
			this.disabled = true;

			//ExternalInterface.addCallback("browse", this, browse);
			ExternalInterface.addCallback("setProperties", this, setProperties);
			ExternalInterface.addCallback("removeFile", this, removeFile);
			ExternalInterface.addCallback("uploadFile", this, uploadFile);
			ExternalInterface.addCallback("cancelUploadFile", this, cancelUploadFile);
			ExternalInterface.addCallback("disableAdd", this, disableAdd);
		}
		
		public function setProperties(properties:Object)
		{
			this.acceptedTypes = properties.acceptedTypes;
			this.noDuplicate = properties.noDuplicate;
			this.maxFiles = properties.maxFiles;
			if (!this.acceptedTypes['*'])
			{
				var types:String = "";
				for (var type in this.acceptedTypes)
				{
					types += "*."+type+";";
				}
				this.fileTypes = [{description: types, extension: types}];
			}			
		}
		
		public function disableAdd(isDisabled:Boolean)
		{
			this.disabled = isDisabled;
		}
		
		public function isDisabled()
		{
			return this.disabled;
		}
		
		public function removeFile(index:Number)
		{
			if (index<this.uploadIndex) this.uploadIndex--;
			this.fileRefList.splice(index, 1);
			this.fileDataList.splice(index, 1);
			return this.uploadIndex;
		}
		
		public function uploadFile(index, actionURL, postData)
		{
			this.uploadIndex = index;
			var item: FileReference = fileRefList[index];
			if (item) 
			{
				if (postData) item["postData"] = postData;
				item.upload(actionURL);
				return item.size;
			}
			return -1;
		}
		
		public function cancelUploadFile(index) {
			var item: FileReference = fileRefList[index];
			if (item)
			{
				item.cancel();
			}
		}
		
		
		public function onSelect (frl: FileReferenceList) {

		    var list: Array = frl.fileList;
		    var item: FileReference;
		    for (var i:Number = 0; i < list.length; i++) {
		    	if (this.fileRefList.length==this.maxFiles) break;
		        item = list[i];
			    if (!this.checkFileType(item.name) || !this.checkDuplicated(item.name)) {
					ExternalInterface.call(this.getComponentString()+"._flashFireEvent('ontyperejected', {})");
					continue;  
				}
				item.addListener(this);
		        this.fileRefList.push(item);
		        this.fileDataList.push("{creationDate: "+this.getJSDateString(item.creationDate)+", creator: '"+item.creator+"', modificationDate: "+this.getJSDateString(item.modificationDate)+", name: '"+escapeString(item.name)+"', size: "+item.size+", type: '"+item.type+"'}");
		    }
		    ExternalInterface.call(this.getComponentString()+"._flashAdd(["+this.fileDataList.join(",")+"])");
		}

		public function onCancel() {
		}
	
		public function onProgress (file:FileReference, bytesLoaded:Number, bytesTotal:Number) {
			ExternalInterface.call(this.getComponentString()+"._flashOnProgress("+bytesLoaded+", "+bytesTotal+")");
		}
		
		public function onComplete (file:FileReference) {
			ExternalInterface.call(this.getComponentString()+"._flashOnComplete()");
		}
		
		public function onHTTPError (file:FileReference, httpError:Number): Void {
			ExternalInterface.call(this.getComponentString()+"._flashHTTPError("+httpError+")");
		}
		public function onIOError (file:FileReference) {
			ExternalInterface.call(this.getComponentString()+"._flashIOError()");
		}
		public function onSecurityError (file:FileReference, errorString:String) {
			ExternalInterface.call(this.getComponentString()+"._flashOnSecurityError('"+errorString+"')");
		}
		
		public function browse() {
			var fileRef:FileReferenceList = new FileReferenceList();
			fileRef.addListener(this);
			//[{description: "Image files", extension: "*.jpg;*.gif;*.png", macType: "JPEG;jp2_;GIFf;PNGf"}, {description: "Flash Movies", extension: "*.swf", macType: "SWFL"}]	
			if (this.fileTypes) fileRef.browse(this.fileTypes); else fileRef.browse();
		}

		public function getComponentString()
		{
			return "FlashFileUpload.getComponent('"+_root.fileUploadId+"')";
		}
		
		private function checkFileType (fileType) {
			if (!this.acceptedTypes || this.acceptedTypes['*']) { return true; }
			var p:Number = fileType.lastIndexOf('.');
			if (p!=-1) fileType = fileType.substr(p+1); else fileType="";
			if (this.acceptedTypes[fileType.toLowerCase()]) {
				return true;
			}
			return false;
		}
	
		private function checkDuplicated (fileName:String) {
			if (!this.noDuplicate) return true;
			for (var i = 0; i < this.fileRefList.length; i++) {
				if (fileName == this.fileRefList[i].name) {
					return false;
				}
			}
			return true;
		}
		
		private function getJSDateString(d:Date) {
			var result:String = "new Date(";
			result+=d.getFullYear()+","+d.getMonth()+","+d.getDate()+","+d.getHours()+","+d.getMinutes()+","+d.getSeconds()+","+d.getMilliseconds()+")";
			return result; 
		}
		
		private function escapeString(str:String)
		{
			return str.split("'").join("\\'").split("\"").join("\\\"");
		}
		
		private function jstrace (msg: String)
		{
			ExternalInterface.call("FlashFileUpload.ASTrace", msg);
		}

		private function jsalert (msg: String)
		{
			ExternalInterface.call("FlashFileUpload.ASAlert", msg);
		}

		public static function main(swfRoot:MovieClip):Void 
		{
			// entry point
			swfRoot.app = new FileUploadComponent(swfRoot);
			var mc = swfRoot.createEmptyMovieClip("mc", swfRoot.getNextHighestDepth());
			mc.beginFill(0xffff80, 0);
				mc.moveTo(0, 0);
				mc.lineTo(2048, 0);
				mc.lineTo(2048, 2048);
				mc.lineTo(0, 2048);
				mc.lineTo(0, 0);
			mc.endFill();	
			mc.onRelease = function () { }
			
			swfRoot.onMouseUp = function ()
			{
				if (swfRoot.app.isDisabled()) return;
				swfRoot.app.browse();
				ExternalInterface.call(swfRoot.app.getComponentString() + "._flashClearFocus()");
			}
			
			ExternalInterface.call(swfRoot.app.getComponentString() + "._flashSetComponent()");
			
			swfRoot.gotoAndStop(1);
			swfRoot.onEnterFrame = function ()
			{
			};
		}		
}