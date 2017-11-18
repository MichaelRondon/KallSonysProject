(function () {
    'use strict';

    angular
            .module('omsApp')
            .controller('OrdenesCerradasController', OrdenesCerradasController);

    OrdenesCerradasController.$inject = ['ParseLinks', 'AlertService', 'paginationConstants', 'Ordenes', 'pagingParams'];

    function OrdenesCerradasController(ParseLinks, AlertService, paginationConstants, Ordenes, pagingParams) {

        var vm = this;
        
        vm.fechaCierre;
        vm.datePickerOpenStatus = {};
        vm.consultar=consultar;
        vm.openCalendar = openCalendar;
        vm.page = 0;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        vm.loadPage = loadPage;
        vm.links = {
            last: 0
        };
        vm.reset = reset;
        
        function consultar() {
            if(!vm.fechaCierre){
                return;
            }
            Ordenes.cerradas({
//                page: vm.page,
//                size: vm.itemsPerPage,
//                sort: sort(),
                fecha: vm.fechaCierre
                
            }, onSuccess, onError);
//            function sort() {
//                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
//                if (vm.predicate !== 'id') {
//                    result.push('id');
//                }
//                return result;
//            }

            function onSuccess(data, headers) {
//                vm.links = ParseLinks.parse(headers('link'));
//                vm.totalItems = headers('X-Total-Count');
//                vm.queryCount = vm.totalItems;
                vm.orden = data;
//                vm.page = pagingParams.page;
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

        vm.datePickerOpenStatus.fechaCierre = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
