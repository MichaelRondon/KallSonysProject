(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('ProveedorProductoController', ProveedorProductoController);

    ProveedorProductoController.$inject = ['ProveedorProducto'];

    function ProveedorProductoController(ProveedorProducto) {

        var vm = this;

        vm.proveedorProductos = [];

        loadAll();

        function loadAll() {
            ProveedorProducto.query(function(result) {
                vm.proveedorProductos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
