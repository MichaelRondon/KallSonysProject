//(function() {
//    'use strict';
//    angular
//        .module('omsApp')
//        .factory('ProductoImageService', ProductoImageService);
//
//    ProductoImageService.$inject = ['$resource', 'DateUtils'];
//
//    function ProductoImageService ($resource, DateUtils) {
//        var resourceUrl =  'api/imagen-productos/smallImage/:id';
//
//        return $resource(resourceUrl, {}, {
//            'query': { method: 'GET', isArray: true},
//            'get': {
//                method: 'GET',
//                transformResponse: function (data) {
//                    data = angular.fromJson(data);
////                    data.fechaDeRegistro = DateUtils.convertDateTimeFromServer(data.fechaDeRegistro);
//                    return data;
//                }
//            },
//            'update': { method:'PUT' },
//            'download': {
//                url:'api/imagen-productos/smallImage/download/:id',
//                method: 'GET',
//                params:{
//                    id:null
//                },
//                headers: {
//                    accept: 'application/octet-stream'
//                },
//                responseType: 'arraybuffer',
//                cache: true,
//                transformResponse: function(data) {
//                    var file;
//                    if (data) {
//                        file = new Blob([data]);
//                    }
//                    return {
//                        response: file
//                    };
//                }
//            }
//        });
//    }
//})();
