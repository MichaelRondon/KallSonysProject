(function() {
    'use strict';

    angular
        .module('omsApp')
        .controller('OrdenDetailController', OrdenDetailController);

    OrdenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orden', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function OrdenDetailController($scope, $rootScope, $stateParams, previousState, entity, Orden, ParseLinks, AlertService, paginationConstants, pagingParams) {
        var vm = this;

        vm.orden = entity;
        vm.previousState = previousState.name;
        
        vm.items = [];
        
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
//        vm.reset = reset;
        vm.transition = transition;
        vm.reverse = true;
        vm.productImageSmallBaseUrl = 'http://laptop-diego:9091/api/ImageSmall/';
        
        vm.onChange = onChange;
        
        loadAll();

        function loadAll() {
            Orden.ordenDetalle({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort(),
                id: vm.orden.idOrden
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
                vm.items = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        function onChange() {
            vm.productos = [];
            vm.page = 0;
            loadAll();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        var unsubscribe = $rootScope.$on('omsApp:ordenUpdate', function(event, result) {
            vm.orden = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
