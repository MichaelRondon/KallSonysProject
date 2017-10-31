(function () {
    'use strict';

    angular
            .module('omsApp')
            .controller('RankingClientesProducto', RankingClientesProducto);

    RankingClientesProducto.$inject = ['Producto', 'ParseLinks', 'AlertService', 'paginationConstants', 'OrdenesReport'];

    function RankingClientesProducto(Producto, ParseLinks, AlertService, paginationConstants, OrdenesReport) {

        var vm = this;
        
        vm.codigoProducto=null;
        vm.ranking=ranking;
        vm.page = 0;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.productImageSmallBaseUrl = 'http://laptop-diego:9091/api/ImageSmall/';
        
        



        vm.productos = [];
        vm.loadPage = loadPage;
        vm.links = {
            last: 0
        };
        vm.reset = reset;
        loadAll();
        
        function ranking() {
            OrdenesReport.rankingClientes({
                
            })
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
        
        
        

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
