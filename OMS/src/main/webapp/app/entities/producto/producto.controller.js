(function () {
    'use strict';

    angular
            .module('omsApp')
            .controller('ProductoController', ProductoController);

    ProductoController.$inject = ['Producto', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function ProductoController(Producto, ParseLinks, AlertService, paginationConstants) {

        var vm = this;

        vm.productos = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.productImageSmallBaseUrl = 'http://laptop-diego:9091/api/ImageSmall/';
        vm.productImageMediumBaseUrl = 'http://laptop-diego:9091/api/ImageMedium/';
        vm.productImageLargeBaseUrl = 'http://laptop-diego:9091/api/ImageLarge/';
        vm.getKeyWordText = getKeyWordText;
        vm.onChange = onChange;

        vm.codigoProducto = null;
        vm.nombreProducto = null;
        vm.descripcion = null;
        loadAll();

        function loadAll() {
            Producto.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort(),
                codigoProducto: vm.codigoProducto,
                nombreProducto: vm.nombreProducto,
                descripcion: vm.descripcion
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.productos.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset() {
            vm.page = 0;
            vm.productos = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        function getKeyWordText(items) {
            var text = '';
            if (items) {
                var i;
                for (i = 0; i < items.length; i++) {
                    if (i !== 0) {
                        text += ', ';
                    }
                    text += items[i];
                }
            }
            return text;
        }

        function onChange() {
            vm.productos = [];
            vm.page = 0;
            loadAll();
        }
    }
})();
