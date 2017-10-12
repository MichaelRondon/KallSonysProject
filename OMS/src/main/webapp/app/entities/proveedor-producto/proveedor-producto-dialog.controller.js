(function() {
    'use strict';

    angular
            .module('omsApp')
            .controller('ProveedorProductoDialogController', ProveedorProductoDialogController);

    ProveedorProductoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProveedorProducto', 'Proveedor', 'Producto'];

    function ProveedorProductoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProveedorProducto, Proveedor, Producto) {
        var vm = this;

        vm.proveedorProducto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proveedors = Proveedor.query();
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.proveedorProducto.id !== null) {
                ProveedorProducto.update(vm.proveedorProducto, onSaveSuccess, onSaveError);
            } else {
                ProveedorProducto.save(vm.proveedorProducto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omsApp:proveedorProductoUpdate', result);
                $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
