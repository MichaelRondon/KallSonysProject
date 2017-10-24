(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('TarjetaDeleteController',TarjetaDeleteController);

    TarjetaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tarjeta'];

    function TarjetaDeleteController($uibModalInstance, entity, Tarjeta) {
        var vm = this;

        vm.tarjeta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tarjeta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
