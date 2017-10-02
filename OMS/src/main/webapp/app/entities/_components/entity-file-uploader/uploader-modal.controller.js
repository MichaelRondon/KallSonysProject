(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('EntityFileUploadController', EntityFileUploadController);

    EntityFileUploadController.$inject = ['$scope', '$uibModalInstance', 'Upload', 'controllerConfig'];

    function EntityFileUploadController ($scope, $uibModalInstance, Upload, controllerConfig) {
        var vm = this;
        

        vm.file = null;
        vm.errFile = null;
        vm.uploadFiles = uploadFiles;
        vm.fileSize = null;
        vm.uploading=false;
        vm.isUploadDisabled=false;
        var currentUpload=null;
        

        vm.allowedFileTypes=controllerConfig.getAllowedFileTypes();
        vm.allowedFileExtensions=controllerConfig.getAllowedExtensions();
        vm.uploaderTitle=controllerConfig.getTitle(vm);
        vm.allowedFileSize = controllerConfig.getAllowedFileSize();
        
        function uploadFiles (file, errFiles) {
            vm.uploadProgress=0;
            vm.uploadErrorMsg="";
            vm.uploading=true;
            vm.uploadCompleted=false;
            vm.uploadCanceled=false;
            vm.invalidFile=false;
            vm.mexFilesError=false;
            vm.mexSizeError=false;
            vm.maxHeightError=false;

            vm.file = file;
            vm.errFile = errFiles && errFiles[0];

            if(errFiles.length){
                vm.uploadErrorMsg="Ocurrió un error al subir el archivo";

                for(var i in errFiles){
                    var errFile=errFiles[i];
                    if(errFile && errFile.$error && errFile.$error==='pattern'){
                        vm.invalidFile=true;
                    }
                    else if(errFile && errFile.$error && errFile.$error==='maxFiles'){
                        vm.mexFilesError=true;
                    }
                    else if(errFile && errFile.$error && errFile.$error==='maxSize'){
                        vm.mexSizeError=true;
                    }
                    else if(errFile && errFile.$error && errFile.$error==='maxHeight'){
                        vm.maxHeightError=true;
                    }
                }
                vm.uploading=false;
            }
            if (file) {
                //console.log(file);
                currentUpload = Upload.upload({
                    url: controllerConfig.getHttpURL(vm),
                    data: controllerConfig.getHttpRequestParams(vm, file),
                    method:controllerConfig.getHttpRequestMehod(vm)
                });
                currentUpload.then(function (response) {
                    uploadSuccess(response.data);
                    vm.uploadCompleted=true;
                    vm.uploading=false;
                    controllerConfig.onSuccess(vm, response);
                }, function (error) {
                    vm.uploadErrorMsg="Ocurrió un error al subir el archivo";
                    /*if (response.status > 0)
                         vm.uploadErrorMsg +="(" + response.status + ': ' + response.data+")";*/
                    vm.uploading=false;
                    controllerConfig.onError(vm, error);
                }, function (evt) {
                    vm.uploadProgress = Math.min(100, parseInt(100 *
                        evt.loaded / evt.total));
                        ////console.log(currentUpload, currentUpload.abort, vm.uploadProgress);
                });
                currentUpload.catch(function(){
                    vm.uploading=false;
                });
            }
            else{
                vm.uploading=false;
            }
        }

        /*vm.cancelUpload=function(){
            if(currentUpload){
                currentUpload.abort();
                vm.uploading=false;
                vm.uploadCanceled=true;    
                currentUpload=null;
            }
        };*/
        vm.getUploaderContentTemplate=function(){
            return controllerConfig.getContentTemplate(vm);
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };


        function uploadSuccess (result) {
            $scope.$emit(controllerConfig.getUploadSuccessEventName(), result);
        }


        $scope.$on('$destroy',function(){
            $uibModalInstance.close();
        });

        controllerConfig.decorateController(vm, $scope);

    }
})();
