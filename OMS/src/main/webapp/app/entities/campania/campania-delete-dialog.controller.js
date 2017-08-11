(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('CampaniaDeleteController',CampaniaDeleteController);

    CampaniaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Campania'];

    function CampaniaDeleteController($uibModalInstance, entity, Campania) {
        var vm = this;

        vm.campania = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Campania.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
