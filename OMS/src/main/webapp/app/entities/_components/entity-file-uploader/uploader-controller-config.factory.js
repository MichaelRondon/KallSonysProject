(function() {
    'use strict';
    angular
        .module('omsApp')
        .factory('EntityFileUploadControllerConfig', EntityFileUploadControllerConfig);

    EntityFileUploadControllerConfig.$inject = ['FileUploaderUtil', '$q', 'AlertService'];

    function EntityFileUploadControllerConfig (FileUploaderUtil, $q, AlertService) {
        
        function UploaderControllerConfig(customOptions){
            var defaultOptions={
                title:'Subir Archivos',
                resourceURL: '',
                uploadSuccessEventName: '',
                httpRequestMethod:'POST',
                fileParamName:'archivo',
                requestParams:{},
                contentTemplate:null,
                allowedFileSize: '1MB'
            };
            this.options=angular.extend(defaultOptions, customOptions || {});
        }

        angular.extend(UploaderControllerConfig.prototype, {
            getTitle:function(ctrl){
                return this.options.title;
            },
            getHttpURL:function(ctrl){
                if(!this.options.resourceURL)
                    throw 'La URL no se ha especificado';
                return this.options.resourceURL;
            },
            getUploadSuccessEventName:function(){
                if(!this.options.uploadSuccessEventName)
                    throw 'El succss event name no se ha especificado';
                return this.options.uploadSuccessEventName;
            },
            getHttpRequestMehod:function(ctrl){
                return this.options.httpRequestMethod || 'POST';
            },

            getHttpRequestParams:function(ctrl, file){
                this.options.requestParams[this.options.fileParamName]=file;
                return this.options.requestParams;
            },

            

            setFileParamName:function(name){
                this.options.fileParamName=name;
            },

            getContentTemplate:function(ctrl){
                return this.options.contentTemplate;
            },
            getAllowedFileTypes:function(){
                return FileUploaderUtil.getAllowedTypesString();
            },

            getAllowedExtensions:function(){
                return FileUploaderUtil.getAllowedExtensions().join(' ,');
            },

            getAllowedFileSize: function(){
                return this.options.allowedFileSize;
            },

            onError:function(ctrl, error){
                if(error.headers('X-omsApp-error') || error.headers('x-omsApp-error')){
                    var message=error.headers('X-omsApp-error') || error.headers('x-omsApp-error');
                    ctrl.uploadErrorMsg=message;
                }
                else{
                    if(!error.data || !error.data.message)
                        AlertService.defaultError();
                }
            },
            onSuccess:function(ctrl, response){
                
            },

            decorateController:function(ctrl, $scope){
                
            }
        });

        

        return UploaderControllerConfig;

        
    }
})();


