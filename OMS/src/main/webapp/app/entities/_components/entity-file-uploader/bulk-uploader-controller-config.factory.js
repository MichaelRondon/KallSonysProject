(function() {
    'use strict';
    angular
        .module('siccApp')
        .factory('BulkFileUploadControllerConfig', BulkFileUploadControllerConfig);

    BulkFileUploadControllerConfig.$inject = ['EntityFileUploadControllerConfig', 'FileUploaderUtil', 'SICCParams'];

    function BulkFileUploadControllerConfig (EntityFileUploadControllerConfig, FileUploaderUtil, SICCParams) {
        var BulkUploaderControllerConfig=function(){
            EntityFileUploadControllerConfig.apply(this, arguments);
        };
        angular.extend(BulkUploaderControllerConfig.prototype,EntityFileUploadControllerConfig.prototype);
        angular.extend(BulkUploaderControllerConfig.prototype,{
            decorateController:function(vm, $scope){
                var entityName=this.options.entityName;
                vm.csvTemplateURL=this.getCSVTemplateDownloabledURL(entityName);
            },
            // BulkUploaderControllerConfig.prototype=Object.create(EntityFileUploadControllerConfig.prototype);
            getAllowedFileTypes:function(){
                return FileUploaderUtil.getCSVAllowedMimeType().type+","+this.getAllowedExtensions();
            },

            getAllowedExtensions:function(){
                return FileUploaderUtil.getCSVAllowedMimeType().extensions.join();
            },
            onSuccess:function(ctrl, response){
                
            },
            getContentTemplate:function(ctrl){
                var TEMPLATE_CSV_CARGA_MASIVA_URL="app/entities/_components/entity-file-uploader/templates/bulk-uploader-content.html";
                if(this.options.contentTemplate)
                    return this.options.contentTemplate;
                return TEMPLATE_CSV_CARGA_MASIVA_URL;
            },
            getCSVTemplateDownloabledURL:function(entityName){
                return SICCParams.getRutaPlantillaCSVBulk(entityName);
            }
        });

        
        return BulkUploaderControllerConfig;        
    }
})();


