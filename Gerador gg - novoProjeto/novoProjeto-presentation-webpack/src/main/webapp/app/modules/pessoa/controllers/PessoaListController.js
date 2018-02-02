PessoaListController.$inject = ['$scope', 'PessoaService', 'gumgaController', '$gmdAlert'];

function PessoaListController($scope, PessoaService, gumgaController, $gmdAlert) {
  PessoaService.resetDefaultState();
  gumgaController.createRestMethods($scope, PessoaService, 'pessoa');


  $scope.pessoa.execute('get');

  $scope.pessoa.on('deleteSuccess', function() {
    $gmdAlert.success('Sucesso!', 'Seu registro foi removido!', 2000);
    $scope.pessoa.execute('get');
  });

  $scope.actions = [
    { key: 'option1', label: 'option1' },
    { key: 'option2', label: 'option2' }
  ];

  $scope.search = function(field, param) {
    $scope.query = { searchFields: [field], q: param }
    $scope.pessoa.methods.search(field,param)
  }

  $scope.advancedSearch = function(param) {
    $scope.pessoa.methods.advancedSearch(param)
  }

  $scope.action = function(queryaction) {
    console.log(queryaction);
  }

  $scope.tableConfig = {
    columns: 'nome ,button',
    checkbox: true,
    selection: 'multi',
    materialTheme: true,
    itemsPerPage: [5, 10, 15, 30],
    empty: {
      enabled: true
    },
    columnsConfig: [{
      name: 'nome',
      title: '<span gumga-translate-tag="pessoa.nome"> nome </span>',
      content: '{{$value.nome }}',
      sortField: 'nome'
    }, {
      name: 'button',
      title: ' ',
      content: '<span class="pull-right"><a class="btn gmd btn-link gmd-ripple" ui-sref="pessoa.edit({id: $value.id })"><i class="glyphicon glyphicon-pencil"></i></a></span>'
    }]
  };

};

module.exports = PessoaListController;
