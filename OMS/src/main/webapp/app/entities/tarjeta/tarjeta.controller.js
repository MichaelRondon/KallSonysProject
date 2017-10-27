(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('TarjetaController', TarjetaController);

    TarjetaController.$inject = ['Tarjeta'];

    function TarjetaController(Tarjeta) {

        var vm = this;

        vm.tarjetas = [];

        loadAll();

        function loadAll() {
            Tarjeta.query(function(result) {
                vm.tarjetas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
