(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('CampaniaDialogController', CampaniaDialogController);

    CampaniaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Campania', 'Producto'];

    function CampaniaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Campania, Producto) {
        var vm = this;

        vm.campania = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.campania.id !== null) {
                Campania.update(vm.campania, onSaveSuccess, onSaveError);
            } else {
                Campania.save(vm.campania, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omsApp:campaniaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaInicio = false;
        vm.datePickerOpenStatus.fechaFin = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
