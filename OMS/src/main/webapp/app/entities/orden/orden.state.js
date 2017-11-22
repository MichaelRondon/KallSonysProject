(function() {
    'use strict';

    angular
        .module('omsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('orden', {
            parent: 'entity',
            url: '/orden?page&sort&search',
            data: {
                authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON'],
                pageTitle: 'omsApp.orden.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orden/orden.html',
                    controller: 'OrdenController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('orden-detail', {
            parent: 'orden',
            url: '/orden/{id}',
            data: {
                authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON'],
                pageTitle: 'omsApp.orden.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orden/orden-detail.html',
                    controller: 'OrdenDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Orden', function($stateParams, Orden) {
                    return Orden.ordenTotal({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'orden',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('orden-detail.edit', {
            parent: 'orden-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden/orden-dialog.html',
                    controller: 'OrdenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orden', function(Orden) {
                            return Orden.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orden.new', {
            parent: 'orden',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden/orden-dialog.html',
                    controller: 'OrdenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orden: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('orden', null, { reload: 'orden' });
                }, function() {
                    $state.go('orden');
                });
            }]
        })
        .state('orden.edit', {
            parent: 'orden',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden/orden-dialog.html',
                    controller: 'OrdenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orden', function(Orden) {
                            return Orden.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orden', null, { reload: 'orden' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orden.delete', {
            parent: 'orden',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden/orden-delete-dialog.html',
                    controller: 'OrdenDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Orden', function(Orden) {
                            return Orden.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orden', null, { reload: 'orden' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
