PessoaFormController.$inject = ['PessoaService', '$state', 'entity', '$scope', 'gumgaController', '$gmdAlert'];

function PessoaFormController(PessoaService, $state, entity, $scope, gumgaController,$gmdAlert) {
	$scope.continue = !$state.params.id
	PessoaService.resetDefaultState();
	gumgaController.createRestMethods($scope, PessoaService, 'pessoa');




	$scope.pessoa.data = angular.copy(entity.data) || {};

	$scope.pessoa.on('putSuccess',function(data) {
		$gmdAlert.success('Sucesso!', 'Seu registro foi adicionado!', 3000);
		if($scope.shouldContinue) {
			$scope.pessoa.data  = {};
		} else {
			$state.go('pessoa.list');
		}
	});

	$scope.pessoa.on('putError',function(data) {
		$gmdAlert.error('Ops!', 'Acho que algo deu errado!', 3000);
	});
}

module.exports = PessoaFormController;
