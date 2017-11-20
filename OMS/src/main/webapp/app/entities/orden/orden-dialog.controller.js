(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('OrdenDialogController', OrdenDialogController);

    OrdenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orden'];

    function OrdenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Orden) {
        var vm = this;

        vm.orden = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orden.id !== null) {
                Orden.update(vm.orden, onSaveSuccess, onSaveError);
            } else {
                Orden.save(vm.orden, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omsApp:ordenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
