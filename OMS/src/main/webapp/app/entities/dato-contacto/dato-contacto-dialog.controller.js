(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('DatoContactoDialogController', DatoContactoDialogController);

    DatoContactoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DatoContacto', 'Proveedor'];

    function DatoContactoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DatoContacto, Proveedor) {
        var vm = this;

        vm.datoContacto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proveedors = Proveedor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.datoContacto.id !== null) {
                DatoContacto.update(vm.datoContacto, onSaveSuccess, onSaveError);
            } else {
                DatoContacto.save(vm.datoContacto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omsApp:datoContactoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
