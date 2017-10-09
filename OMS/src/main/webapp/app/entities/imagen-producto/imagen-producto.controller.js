//(function() {
//    'use strict';
//
//    angular
//        .module('omsApp')
//        .controller('ImagenProductoController', ImagenProductoController);
//
//    ImagenProductoController.$inject = ['ArchivosListControllerFactory', '$scope', 'pagingParams'];
//
//    function ImagenProductoController (ArchivosListControllerFactory, $scope, pagingParams) {
//
//        var ArchivosListController=ArchivosListControllerFactory.create($scope, pagingParams);
//        var controller=new ArchivosListController({
//            entityName:"imagen-producto",
//            parentEntityType:"producto",
//            parentFilterParamName:"id_product"
//            
//        });
//        return controller;
//    }
//})();
