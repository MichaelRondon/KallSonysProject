(function() {
    'use strict';

    angular
        .module('omsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('proveedor-producto', {
            parent: 'entity',
            url: '/proveedor-producto',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omsApp.proveedorProducto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proveedor-producto/proveedor-productos.html',
                    controller: 'ProveedorProductoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proveedorProducto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('proveedor-producto-detail', {
            parent: 'proveedor-producto',
            url: '/proveedor-producto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omsApp.proveedorProducto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proveedor-producto/proveedor-producto-detail.html',
                    controller: 'ProveedorProductoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proveedorProducto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProveedorProducto', function($stateParams, ProveedorProducto) {
                    return ProveedorProducto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'proveedor-producto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('proveedor-producto-detail.edit', {
            parent: 'proveedor-producto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proveedor-producto/proveedor-producto-dialog.html',
                    controller: 'ProveedorProductoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProveedorProducto', function(ProveedorProducto) {
                            return ProveedorProducto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proveedor-producto.new', {
            parent: 'proveedor-producto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proveedor-producto/proveedor-producto-dialog.html',
                    controller: 'ProveedorProductoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idProductoEnProveedor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('proveedor-producto', null, { reload: 'proveedor-producto' });
                }, function() {
                    $state.go('proveedor-producto');
                });
            }]
        })
        .state('proveedor-producto.edit', {
            parent: 'proveedor-producto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proveedor-producto/proveedor-producto-dialog.html',
                    controller: 'ProveedorProductoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProveedorProducto', function(ProveedorProducto) {
                            return ProveedorProducto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proveedor-producto', null, { reload: 'proveedor-producto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proveedor-producto.delete', {
            parent: 'proveedor-producto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proveedor-producto/proveedor-producto-delete-dialog.html',
                    controller: 'ProveedorProductoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProveedorProducto', function(ProveedorProducto) {
                            return ProveedorProducto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proveedor-producto', null, { reload: 'proveedor-producto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
