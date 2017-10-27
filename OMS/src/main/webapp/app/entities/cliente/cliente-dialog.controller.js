(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('ClienteDialogController', ClienteDialogController);

    ClienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Cliente', 'Tarjeta'];

    function ClienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Cliente, Tarjeta) {
        var vm = this;

        vm.cliente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.isMatch = true;
        vm.change = change;
        vm.datostarjetas = Tarjeta.query({filter: 'cliente-is-null'});
        $q.all([vm.cliente.$promise, vm.datostarjetas.$promise]).then(function() {
            if (!vm.cliente.datosTarjetaId) {
                return $q.reject();
            }
            return Tarjeta.get({id : vm.cliente.datosTarjetaId}).$promise;
        }).then(function(datosTarjeta) {
            vm.datostarjetas.push(datosTarjeta);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function change () {
            vm.isMatch = !(vm.cliente.password) || vm.cliente.password === vm.password_rep;
        }

        function save () {
            vm.isSaving = true;
            if (vm.cliente.id !== null) {
                Cliente.update(vm.cliente, onSaveSuccess, onSaveError);
            } else {
                Cliente.save(vm.cliente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('omsApp:clienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
