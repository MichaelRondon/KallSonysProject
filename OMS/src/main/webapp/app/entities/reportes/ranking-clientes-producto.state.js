(function () {
    'use strict';

    angular
            .module('omsApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('ranking-clientes-producto', {
                    parent: 'entity',
                    url: '/reportes',
                    data: {
                        authorities: ['ROLE_USER'],
                        pageTitle: 'Reporte clienes por producto'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/reportes/ranking-clientes-producto.html',
                            controller: 'RankingClientesProducto',
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
                            }],
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('categoria');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }]
                    }
                })
//                .state('producto-detail', {
//                    parent: 'producto',
//                    url: '/producto/{id}',
//                    data: {
//                        authorities: ['ROLE_USER'],
//                        pageTitle: 'omsApp.producto.detail.title'
//                    },
//                    views: {
//                        'content@': {
//                            templateUrl: 'app/entities/producto/producto-detail.html',
//                            controller: 'ProductoDetailController',
//                            controllerAs: 'vm'
//                        }
//                    },
//                    resolve: {
//                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                                $translatePartialLoader.addPart('producto');
//                                $translatePartialLoader.addPart('estado');
//                                return $translate.refresh();
//                            }],
//                        entity: ['$stateParams', 'Producto', function ($stateParams, Producto) {
//                                return Producto.get({id: $stateParams.id}).$promise;
//                            }],
//                        previousState: ["$state", function ($state) {
//                                var currentStateData = {
//                                    name: $state.current.name || 'producto',
//                                    params: $state.params,
//                                    url: $state.href($state.current.name, $state.params)
//                                };
//                                return currentStateData;
//                            }]
//                    }
//                })
//                .state('producto-detail.edit', {
//                    parent: 'producto-detail',
//                    url: '/detail/edit',
//                    data: {
//                        authorities: ['ROLE_USER']
//                    },
//                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
//                            $uibModal.open({
//                                templateUrl: 'app/entities/producto/producto-dialog.html',
//                                controller: 'ProductoDialogController',
//                                controllerAs: 'vm',
//                                backdrop: 'static',
//                                size: 'lg',
//                                resolve: {
//                                    entity: ['Producto', function (Producto) {
//                                            return Producto.get({id: $stateParams.id}).$promise;
//                                        }]
//                                }
//                            }).result.then(function () {
//                                $state.go('^', {}, {reload: false});
//                            }, function () {
//                                $state.go('^');
//                            });
//                        }]
//                })
//                .state('producto.new', {
//                    parent: 'producto',
//                    url: '/new',
//                    data: {
//                        authorities: ['ROLE_USER']
//                    },
//                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
//                            $uibModal.open({
//                                templateUrl: 'app/entities/producto/producto-dialog.html',
//                                controller: 'ProductoDialogController',
//                                controllerAs: 'vm',
//                                backdrop: 'static',
//                                size: 'lg',
//                                resolve: {
//                                    entity: function () {
//                                        return {
//                                            nombre: null,
//                                            descripcion: null,
//                                            precio: null,
//                                            estado: null,
//                                            disponibilidad: null,
//                                            fechaRevDisponibilidad: null,
//                                            marca: null,
//                                            keyWords: null,
//                                            id: null
//                                        };
//                                    }
//                                }
//                            }).result.then(function () {
//                                $state.go('producto', null, {reload: 'producto'});
//                            }, function () {
//                                $state.go('producto');
//                            });
//                        }]
//                })
//                .state('producto.edit', {
//                    parent: 'producto',
//                    url: '/{id}/edit',
//                    data: {
//                        authorities: ['ROLE_USER']
//                    },
//                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
//                            $uibModal.open({
//                                templateUrl: 'app/entities/producto/producto-dialog.html',
//                                controller: 'ProductoDialogController',
//                                controllerAs: 'vm',
//                                backdrop: 'static',
//                                size: 'lg',
//                                resolve: {
//                                    entity: ['Producto', function (Producto) {
//                                            return Producto.get({id: $stateParams.id}).$promise;
//                                        }]
//                                }
//                            }).result.then(function () {
//                                $state.go('producto', null, {reload: 'producto'});
//                            }, function () {
//                                $state.go('^');
//                            });
//                        }]
//                })
//                .state('producto.delete', {
//                    parent: 'producto',
//                    url: '/{id}/delete',
//                    data: {
//                        authorities: ['ROLE_USER']
//                    },
//                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
//                            $uibModal.open({
//                                templateUrl: 'app/entities/producto/producto-delete-dialog.html',
//                                controller: 'ProductoDeleteController',
//                                controllerAs: 'vm',
//                                size: 'md',
//                                resolve: {
//                                    entity: ['Producto', function (Producto) {
//                                            return Producto.get({id: $stateParams.id}).$promise;
//                                        }]
//                                }
//                            }).result.then(function () {
//                                $state.go('producto', null, {reload: 'producto'});
//                            }, function () {
//                                $state.go('^');
//                            });
//                        }]
//                })
                ;
    }

})();
