(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('ProveedorProductoDeleteController',ProveedorProductoDeleteController);

    ProveedorProductoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProveedorProducto'];

    function ProveedorProductoDeleteController($uibModalInstance, entity, ProveedorProducto) {
        var vm = this;

        vm.proveedorProducto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProveedorProducto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
