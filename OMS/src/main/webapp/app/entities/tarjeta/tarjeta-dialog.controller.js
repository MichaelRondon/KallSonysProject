(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('TarjetaDialogController', TarjetaDialogController);

    TarjetaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tarjeta'];

    function TarjetaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tarjeta) {
        var vm = this;

        vm.tarjeta = entity;
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
            if (vm.tarjeta.id !== null) {
                Tarjeta.update(vm.tarjeta, onSaveSuccess, onSaveError);
            } else {
                Tarjeta.save(vm.tarjeta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omsApp:tarjetaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
