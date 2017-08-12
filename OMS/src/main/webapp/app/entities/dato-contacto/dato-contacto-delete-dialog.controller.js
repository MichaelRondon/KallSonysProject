(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('DatoContactoDeleteController',DatoContactoDeleteController);

    DatoContactoDeleteController.$inject = ['$uibModalInstance', 'entity', 'DatoContacto'];

    function DatoContactoDeleteController($uibModalInstance, entity, DatoContacto) {
        var vm = this;

        vm.datoContacto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DatoContacto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
