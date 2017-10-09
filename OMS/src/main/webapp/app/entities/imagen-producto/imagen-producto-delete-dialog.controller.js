//(function() {
//    'use strict';
//
//    angular
//        .module('omsApp')
//        .controller('ImagenProductoDeleteController',ImagenProductoDeleteController);
//
//    ImagenProductoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Producto'];
//
//    function ImagenProductoDeleteController($uibModalInstance, entity, Producto) {
//        var vm = this;
//        vm.archivoInspeccionBaches = entity;
//        vm.clear = function() {
//            $uibModalInstance.dismiss('cancel');
//        };
//        vm.confirmDelete = function (id) {
//            Producto.delete({id: id},
//                function () {
//                    $uibModalInstance.close(true);
//                });
//        };
//    }
//})();
