(function () {
    'use strict';

    angular
            .module('omsApp')
            .controller('OrdenesAbiertasController', OrdenesAbiertasController);

    OrdenesAbiertasController.$inject = ['ParseLinks', 'AlertService', 'paginationConstants', 'Ordenes', 'pagingParams'];

    function OrdenesAbiertasController(ParseLinks, AlertService, paginationConstants, Ordenes, pagingParams) {

        var vm = this;
        
        vm.consultar=consultar;
        vm.page = 0;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.ordenes = [];

        vm.loadPage = loadPage;
        vm.links = {
            last: 0
        };
        vm.reset = reset;
//        loadAll();
        
        function consultar() {
            Ordenes.abiertas({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
                
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
                vm.page = pagingParams.page;
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
        }

        function loadPage(page) {
            vm.page = page;
        }
    }
})();
