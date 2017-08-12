(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('ProveedorDialogController', ProveedorDialogController);

    ProveedorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Proveedor', 'DatoContacto', 'ProveedorProducto'];

    function ProveedorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Proveedor, DatoContacto, ProveedorProducto) {
        var vm = this;

        vm.proveedor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.datocontactos = DatoContacto.query();
        vm.proveedorproductos = ProveedorProducto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.proveedor.id !== null) {
                Proveedor.update(vm.proveedor, onSaveSuccess, onSaveError);
            } else {
                Proveedor.save(vm.proveedor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omsApp:proveedorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
