(function() {
    'use strict';

    angular
        .module('omsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('campania', {
            parent: 'entity',
            url: '/campania?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omsApp.campania.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/campania/campanias.html',
                    controller: 'CampaniaController',
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
                    $translatePartialLoader.addPart('campania');
                    $translatePartialLoader.addPart('estado');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('campania-detail', {
            parent: 'campania',
            url: '/campania/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'omsApp.campania.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/campania/campania-detail.html',
                    controller: 'CampaniaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('campania');
                    $translatePartialLoader.addPart('estado');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Campania', function($stateParams, Campania) {
                    return Campania.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'campania',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('campania-detail.edit', {
            parent: 'campania-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/campania/campania-dialog.html',
                    controller: 'CampaniaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Campania', function(Campania) {
                            return Campania.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('campania.new', {
            parent: 'campania',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/campania/campania-dialog.html',
                    controller: 'CampaniaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                descripcion: null,
                                estado: null,
                                fechaInicio: null,
                                fechaFin: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('campania', null, { reload: 'campania' });
                }, function() {
                    $state.go('campania');
                });
            }]
        })
        .state('campania.edit', {
            parent: 'campania',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/campania/campania-dialog.html',
                    controller: 'CampaniaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Campania', function(Campania) {
                            return Campania.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('campania', null, { reload: 'campania' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('campania.delete', {
            parent: 'campania',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/campania/campania-delete-dialog.html',
                    controller: 'CampaniaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Campania', function(Campania) {
                            return Campania.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('campania', null, { reload: 'campania' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
