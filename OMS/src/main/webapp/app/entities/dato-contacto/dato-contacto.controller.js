(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('DatoContactoController', DatoContactoController);

    DatoContactoController.$inject = ['DatoContacto'];

    function DatoContactoController(DatoContacto) {

        var vm = this;

        vm.datoContactos = [];

        loadAll();

        function loadAll() {
            DatoContacto.query(function(result) {
                vm.datoContactos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
