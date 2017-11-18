(function () {
    'use strict';

    angular
            .module('omsApp')
            .controller('OrdenesProductoController', OrdenesProductoController);

    OrdenesProductoController.$inject = ['Producto', 'ParseLinks', 'AlertService', 'paginationConstants', 'Ordenes', 'pagingParams'];

    function OrdenesProductoController(Producto, ParseLinks, AlertService, paginationConstants, Ordenes, pagingParams) {

        var vm = this;
        
        vm.codigoProducto=null;
        vm.consultar=consultar;
        vm.page = 0;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.productImageSmallBaseUrl = 'http://laptop-diego:9091/api/ImageSmall/';
        vm.ordenes = [];
        vm.showResults = {
            show  : false
          };
        



        vm.producto=null;
        vm.loadPage = loadPage;
        vm.links = {
            last: 0
        };
        vm.reset = reset;
//        loadAll();
        
        function consultar() {
            if(vm.codigoProducto === null || vm.codigoProducto === ''){
                return;
            }
            Ordenes.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort(),
                idProducto: vm.codigoProducto
                
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
                vm.queryCount = vm.totalItems;
                vm.ordenes = data;
                vm.producto=Producto.get({id: vm.codigoProducto});
                vm.page = pagingParams.page;
                vm.showResults.show=true;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function reset() {
            vm.page = 0;
            vm.ordenes = [];
        }

        function loadPage(page) {
            vm.page = page;
        }
    }
})();
