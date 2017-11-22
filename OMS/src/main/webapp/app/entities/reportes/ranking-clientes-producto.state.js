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
                    url: '/ranking-clientes-producto',
                    data: {
                        authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON','CLIENTES_CONSULTA','CLIENTES_ADMON'],
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
                .state('ordenes-producto', {
                    parent: 'entity',
                    url: '/ordenes-producto',
                    data: {
                        authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON','PRODUCTOS_ADMON','PRODUCTOS_CONSULTA'],
                        pageTitle: 'Reporte ordenes por producto'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/reportes/ordenes-producto.html',
                            controller: 'OrdenesProductoController',
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
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }]
                    }
                })
                .state('ordenes-abiertas', {
                    parent: 'entity',
                    url: '/ordenes-abiertas',
                    data: {
                        authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON'],
                        pageTitle: 'Reporte ordenes abiertas'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/reportes/ordenes-abiertas.html',
                            controller: 'OrdenesAbiertasController',
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
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }]
                    }
                })
                .state('ordenes-cerradas', {
                    parent: 'entity',
                    url: '/ordenes-cerradas',
                    data: {
                        authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON'],
                        pageTitle: 'Reporte ordenes cerradas'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/reportes/ordenes-cerradas.html',
                            controller: 'OrdenesCerradasController',
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
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }]
                    }
                })
                .state('ranking-ordenes-cerradas', {
                    parent: 'entity',
                    url: '/ranking-ordenes-cerradas',
                    data: {
                        authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON'],
                        pageTitle: 'Reporte ordenes cerradas'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/reportes/ranking-ordenes-cerradas.html',
                            controller: 'RankingOrdenesCerrradasController',
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
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }]
                    }
                })
                .state('ranking-clientes-fecha', {
                    parent: 'entity',
                    url: '/ranking-clientes-fecha',
                    data: {
                        authorities: ['ROLE_USER','ORDENES_CONSULTA','ORDENES_ADMON','CLIENTES_CONSULTA','CLIENTES_ADMON'],
                        pageTitle: 'Reporte clientes por facturacion'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/reportes/ranking-clientes-fecha.html',
                            controller: 'RankingClientesFechaController',
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
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }]
                    }
                })
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
