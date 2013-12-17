package redboxws

import grails.rest.RestfulController

class DatasetController extends RestfulController{
	static scaffold = true
	static reponseFormats =['atom']
	Dataset dataset
	
	DatasetController(){
		super(Dataset)
	}

	def index(Integer max) {
	   params.max = Math.min(max ?: 10, 100)
	   respond Dataset.list(params), model:[datasetCount: Dataset.count()]
	}
	
	def show(Dataset dataset) {
		if(dataset == null){
		   render status :404
		} 		
		respond dataset
	}
	
	def save(Dataset dataset){
		saveDataset(dataset)
	}

	def edit(Dataset dataset) {
		respond dataset
	}
	
	def update(Dataset dataset) {
		if(dataset == null) {
			render status: NOT_FOUND
		}else{
		 updateDataset(dataset)
		}
	}
		
	def saveDataset(Dataset dataset){	
		// first check for errors then save or update
		    if(dataset.hasErrors()){
			   respond dataset.errors, view:'create'
		    }else {
		      dataset.save flush:true
		        withFormat {
			     xml {
				   flash.message = message(code: 'default.created.message', args: [message(code: 'dataset.label', default: 'Dataset'), dataset.id])
				    redirect dataset
			     }
			   '*' { render status: CREATED }
		      }
		   }	
		}	
	
	def updateDataset(Dataset dataset){
		// first check for errors then save or update
			if(dataset.hasErrors()){
			   respond dataset.errors, view:'edit'
			}else {
			  dataset.save flush:true
				withFormat {
				 xml {
				   flash.message = message(code: 'default.updated.message', args: [message(code: 'dataset.label', default: 'Dataset'), dataset.id])
					redirect dataset
				 }
			   '*' { render status: OK }
			  }
		  }
	  }
  }