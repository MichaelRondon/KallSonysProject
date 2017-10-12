(function () {
    'use strict';

    angular
        .module('omsApp')
        .factory('FileUploaderUtil', FileUploaderUtil);

    
    
    function FileUploaderUtil () {

        var service = {
            getAllowedMimeTypes:getAllowedMimeTypes,
            getAllowedExtensions:getAllowedExtensions,
            getAllowedTypesString:getAllowedTypesString,
            getAllowedExtensionsString:getAllowedExtensionsString,
            getCSVAllowedMimeType:getCSVAllowedMimeType,
            getDocumentsAllowedMimeType:getDocumentsAllowedMimeType,
            getInformesAllowedMimeTypes:getInformesAllowedMimeTypes
        };

        return service;
    }

    var IMAGES_MIME_TYPES=[
        {
            type:'image/png',
            extensions:['.png']
        },
        {
            type:'image/jpeg',
            extensions:['.jpg','jpeg']
        },
        {
            type:'image/jpg', //Doesn't exist
            extensions:['.jpg']
        },
        {
            type:'image/gif',
            extensions:['.gif']
        }
    ];

    var PLAIN_MIME_TYPES=[
        {
            type:'text/plain',
            extensions:['.txt']
        },
        {
            type:'text/csv',
            extensions:['.csv']
        }
    ];

    var DOCUMENTS_MIME_TYPES=[
        {
            type:'application/vnd.openxmlformats-officedocument.presentationml.presentation',
            extensions:['.pptx']
        },
        {
            type:'application/vnd.ms-powerpoint',
            extensions:['.ppt']
        },
        {
            type:'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
            extensions:['.docx']
        },
        {
            type:'application/msword',
            extensions:['.doc']
        },
        {
            type:'application/pdf',
            extensions:['.pdf']
        },
        {
            type:'application/vnd.ms-excel',
            extensions:['.xls']
        },
        {
            type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
            extensions:['.xlsx']
        }
    ];

    var COMPRESSED_MIME_TYPES=[
        {
            type:'application/x-7z-compressed',
            extensions:['.7z']
        },
        {
            type:'application/x-rar-compressed',
            extensions:['.rar']
        },
        {
            type:'application/zip',
            extensions:['.zip']
        }
    ];


    var ALLOWED_MIME_TYPES=[].concat(IMAGES_MIME_TYPES, PLAIN_MIME_TYPES, DOCUMENTS_MIME_TYPES);

    var allowedMimeTypes;
    var allowedExtensions;
    function getAllowedMimeTypes(){
        if(!allowedMimeTypes){
            allowedMimeTypes = ALLOWED_MIME_TYPES.map(function(item){return item.type;});
            /*angular.forEach(ALLOWED_MIME_TYPES, function(value, key) {
                
              this.push(value.type);
            }, allowedMimeTypes);*/
        }
        return allowedMimeTypes;
    }

    function getAllowedExtensions(){
        if(!allowedExtensions){
            allowedExtensions = ALLOWED_MIME_TYPES.map(function(item){return item.extensions.join(',');});            
        }
        return allowedExtensions;
    }

    function getAllowedTypesString(){
        return getAllowedMimeTypes().concat(getAllowedExtensions()).join(',');
    }

    function getAllowedExtensionsString(){
        return getAllowedExtensions().join(', ');
    }

    // csv

    function getCSVAllowedMimeType(){
        return PLAIN_MIME_TYPES[1]; //Obtiene el objeto de csv en PLAIN_MIME_TYPES
    }


    function getDocumentsAllowedMimeType(){
        return DOCUMENTS_MIME_TYPES; 
    }

    function getCompressedMimeType() {
        return COMPRESSED_MIME_TYPES;
    }

    function getInformesAllowedMimeTypes() {
        return getDocumentsAllowedMimeType().concat(getCompressedMimeType()); 
    }
})();
