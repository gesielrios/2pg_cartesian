package conflicts;

import java.util.LinkedList;
import cern.jet.random.engine.MersenneTwister;
import edu.cornell.lassp.houle.RngPack.RandomElement;
import conflicts.sets.IntSet;
import conflicts.sets.ObjectiveSet;
import conflicts.sets.SetOfObjectiveSets;

public class Controller {

	Problem problem;        
	Population pop;
	Relation dominanceRelation;
	Relation[] relations;
	int ds_dim = 10; // dimension of decision space
	int os_dim = 2; // dimension of objective space
    public static final int WEAK_DOMINANCE = 1;
    public static final int EPSILON_DOMINANCE = 2;
    public static final int STRICT_DOMINANCE = 3;
    int current_dominance = Controller.WEAK_DOMINANCE;
    double epsilon = 1;
	DeltaMOSSExactAlgorithm exactAlgo;
	MOSSGreedyAlgorithm greedyAlgoOld;
	DeltaMOSSGreedyAlgorithm greedyAlgo;
    
	
	// default values for the variables:
	public Controller() {
		RandomElement re = new MersenneTwister((int)(System.currentTimeMillis()));
		problem = new RandomProblem_BS(os_dim, re);
		pop = new Population_BS(2, (Problem_BS)problem);
		dominanceRelation = computeWholeWeakRelation(pop);
		relations = new Relation[os_dim];
		for (int i=0; i<os_dim; i++) {
			relations[i] = computeWeakRelation(i, pop);
		}
	}
	
	
	/**
	 * Creates a new Controller with problem prob and given
	 * decision space dimension. 
     * Note that the dominance relations are not computed directly
     * when the Controller is called with this constructor!
	 *  
	 * @param prob             the problem which you want to get solved
	 * @param pop              a population of decision vectors
	 */
     public Controller(Problem prob, Population pop) {
         this.current_dominance = 1;
         this.epsilon = 0.0;
         this.problem = prob;
         this.ds_dim = 1;  // does not matter
         this.os_dim = prob.getObjectiveSpaceDimension();
         this.pop = pop;
     }
     
     
	
	/**
	 * Creates a new Controller with problem prob and given
	 * decision space dimension. The population will be the whole
	 * decision space, the dominance relations will be the weak dominance
	 * relations. They are computed automatically. After this initialization,
	 * you can use the algorithms greedyAlgorithm and exactAlgorithm to 
	 * compute a minimum non-redundant set of objectives which is not
	 * conflicting with the whole set of objectives.
	 *  
	 * @param prob             the problem which you want to get solved
	 * @param decisionSpace    a string which indicates what kind of decision
	 *                         space you want. Should be "1" if the decision
	 *                         space is {0,1}^n (BitString)
	 *                         not yet implemented, only 1 or BitString possible
	 * @param pop              a population of decision vectors
	 * @param kindOfDominance  an int which specifies the dominance relation
	 * 						   weak = 1, epsilon = 2, strict = 3
	 * @param epsilon          the epsilon in the epsilon dominance relation;
	 *                         can be anything if kindOfDominance is != 2
	 *
	 */
     public Controller(Problem prob, int decisionSpace, Population pop, int kindOfDominance, double epsilon) {
         this.current_dominance = kindOfDominance;
         this.epsilon = epsilon;
         init(pop.ds_dim, prob, decisionSpace, pop);
     }
        
     /** The initialization procedure called up in the constructor: */
     private void init(int decisionSpaceDimension, Problem prob, int decisionSpace, Population pop) {
         this.problem = prob;
         this.ds_dim = decisionSpaceDimension;
         this.os_dim = prob.getObjectiveSpaceDimension();
         this.pop = pop;

         /* Computing the dominance relations: */
         switch (this.current_dominance) {
         	case WEAK_DOMINANCE: 
           		this.dominanceRelation = computeWholeWeakRelation(this.pop);
           		this.relations = new Relation[this.os_dim];
           		for (int i=0; i < os_dim; i++) {
           			this.relations[i] = computeWeakRelation(i, pop);
           		}
           		break;
           	case EPSILON_DOMINANCE:
           		this.dominanceRelation = this.computeWholeEpsilonRelation(this.pop);
           		this.relations = new Relation[this.os_dim];
           		for (int i=0; i < os_dim; i++) {
           			this.relations[i] = this.computeEpsilonRelation(i, pop);
           		}
           		break;            		
         }
     }   

     /**
  	 * 
   	 * @return an IntSet which contains the objectives in a minimal non-redundant set
   	 * of objectives, nonconflicting with the whole set of objectives.
   	 */
     public IntSet greedyAlgorithm() {
    	 greedyAlgoOld = new MOSSGreedyAlgorithm(this.os_dim, this.relations, this.dominanceRelation);
    	 return greedyAlgo.performGreedyAlgorithm();
     }
     
     public ObjectiveSet greedyAlgorithmForGivenK(int k) {
    	 greedyAlgo = new DeltaMOSSGreedyAlgorithm(this.pop, 1);
    	 return greedyAlgo.performGreedyAlgorithmGivenK(k);
     }
     
     /**
      * Computes the best aggregation with k objectives according to the 
      * (i)  maximum delta error (if a=1) or
      * (ii) delta error averaged over all solution pairs (if a=2)
      */
     public conflicts.sets.Aggregation greedyAggregationAlgorithmForGivenK(int k, int a) {
    	 greedyAlgo = new DeltaMOSSGreedyAlgorithm(this.pop, 0);
    	 return greedyAlgo.performGreedyAggregationAlgorithmGivenK(k, a);
     }
     
     
     /**
      * Computes the best aggregation with k objectives according to the 
      * (i)  maximum delta error (if a=1) or
      * (ii) delta error averaged over all solution pairs (if a=2)
      * by adding objectives incrementally to the aggregation.
      * 
      * version = 1: each objective is only used at most once
	  * version = 2: all objectives can be used more than once
      * 
      */
     public conflicts.sets.Aggregation greedyIncrementalAggregationAlgorithmForGivenK(int k, int a, int version) {
    	 greedyAlgo = new DeltaMOSSGreedyAlgorithm(this.pop, 0);
    	 return greedyAlgo.performGreedyIncrementalAggregationAlgorithmGivenK(k, version, a);
     }
     
     
     public ObjectiveSet greedyAlgorithmForGivenDelta(double delta) {
    	 greedyAlgo = new DeltaMOSSGreedyAlgorithm(this.pop, 1);
    	 return greedyAlgo.performGreedyAlgorithmGivenDelta(delta);
     }
     
     /**
      * Computes the best aggregation with error of at most delta with respect to the
      * (i)  maximum delta error (if a=1) or
      * (ii) delta error averaged over all solution pairs (if a=2)
      */
     public conflicts.sets.Aggregation greedyAggregationAlgorithmForGivenDelta(double delta, int a) {
    	 greedyAlgo = new DeltaMOSSGreedyAlgorithm(this.pop, 0);
    	 return greedyAlgo.performGreedyAggregationAlgorithmGivenDelta(delta, a);
     }     
     


     /**
      * 
      * @return a minimum set of objectives (integers) which is nonconflicting
      *         with the whole set of objectives 
      */
     public ObjectiveSet exactAlgorithm() {
    	 this.exactAlgo = new DeltaMOSSExactAlgorithm(this.pop, this.os_dim, this.relations);
    	 return exactAlgo.performExactAlgorithm();                        
     }
     
     /**
      * 
      * @return all minimal objective sets with respect to the set of all objectives
      * 		the sizes of which are at most k. 
      */
     public SetOfObjectiveSets allMinimalSetsK(int k) {
    	 this.exactAlgo = new DeltaMOSSExactAlgorithm(this.pop, this.os_dim, this.relations);
    	 return exactAlgo.getAllMinimalSets(2, k);                        
     }
     
     /**
      * 
      * @return all minimal objective sets with respect to the set of all objectives
      * 		the error of which are at most delta. 
      */
     public SetOfObjectiveSets allMinimalSetsDelta(double delta) {
    	 this.exactAlgo = new DeltaMOSSExactAlgorithm(this.pop, this.os_dim, this.relations);
    	 return exactAlgo.getAllMinimalSets(1, delta);                     
     }
     
     /**
      * 
      * @return a minimum set of objectives (integers) which is delta-nonconflicting
      *         with the whole set of objectives 
      */
     public ObjectiveSet exactAlgorithmForGivenDelta(double delta) {
    	 this.exactAlgo = new DeltaMOSSExactAlgorithm(this.pop, this.os_dim, this.relations);
    	 return exactAlgo.performExactAlgorithmGivenDelta(delta);                        
     }

     /**
      * 
      * @return a set of objectives (integers) of size less or equal k the delta failure
      * 		of which is minimal 
      */
     public ObjectiveSet exactAlgorithmForGivenK(int k) {
    	 this.exactAlgo = new DeltaMOSSExactAlgorithm(this.pop, this.os_dim, this.relations);
    	 return exactAlgo.performExactAlgorithmGivenK(k);                        
     }

	/** 
	 * @param p a population of individuals
	 * @return an array of the individuals in p
	 */
	private static Individual[] getIndividuals(Population p) {
		Object[] indis = p.getPopulation().toArray();
		Individual[] inds = new Individual[indis.length];
		for (int i=0; i<inds.length; i++) {			
			inds[i] = (Individual)indis[i];
		}
		return inds;
	}
	
	/**
	 * Computes the epsilon dominance relation for the individuals in the population
	 * with respect to the ith objective, i.e. v epsilon-dominates w iff
	 * epsilon * v_i <= w_i
	 * 
	 * The epsilon used here is always this.epsilon !
	 * 
	 * @param i
	 * @param population 
	 * @return
	 */
	public Relation computeEpsilonRelation(int i, Population population) {
		// not changed....
		Individual[] inds = getIndividuals(population);
		
		Relation rel = new Relation(i, inds.length);
		for (int j=0; j< inds.length; j++) {
			for (int k=0;k<inds.length;k++) {
				if (this.epsilon * inds[j].ov[i] <= inds[k].ov[i]) {
					rel.setinrelation(j,k, true);
				} else {
					rel.setinrelation(j,k, false);
				}
			}
		}		
		
		return rel;
	}
	
	/**
	 * Computes the epsilon dominance relation for the individuals in population,
	 * i.e., v epsilon-dominates w iff for all objectives i, epsilon*v_i<=w_i holds 
	 * 
	 * @param population
	 * @return
	 */
	public Relation computeWholeEpsilonRelation(Population population) {
		Individual[] inds = getIndividuals(population);
		int dim = (population.problem).getObjectiveSpaceDimension();		
		
		Relation rel = new Relation(inds.length);
		for (int i=0; i< inds.length; i++) {
			for (int j=0;j<inds.length;j++) {
				boolean leq = true;
				for (int k=0;k < dim; k++) {
					if (this.epsilon * inds[i].ov[k] > inds[j].ov[k]) {
						leq = false;
						break;
					}					
				}				
				if (leq) {
					rel.setinrelation(i,j, true);
				} else {
					rel.setinrelation(i,j,false);
				}
			}
		}
		return rel;
	}


	/**
	 * Computes the weak dominance relation for the individuals in the population
	 * with respect to the ith objective, i.e. v weakly dominates w iff
	 * v_i <= w_i 
	 * 
	 * @param i
	 * @param population
	 * @return
	 */
	public static Relation computeWeakRelation(int i, Population population) {
		Individual[] inds = getIndividuals(population);
		
		Relation rel = new Relation(i, inds.length);
		for (int j=0; j< inds.length; j++) {
			for (int k=0;k<inds.length;k++) {
				if (inds[j].ov[i] <= inds[k].ov[i]) {
					rel.setinrelation(j,k, true);
				} else {
					rel.setinrelation(j,k, false);
				}
			}
		}		
		
		return rel;
	}
	
	/**
	 * Computes the weak dominance relation for the individuals in population
	 * i.e. v weakly dominates w iff
	 * for all objectives i, v_i<=w_i holds 
	 * 
	 * @param population
	 * @return
	 */
	public static Relation computeWholeWeakRelation(Population population) {
		Individual[] inds = getIndividuals(population);
		int dim = 0;
		if (population.getProblem() != null) {
			dim = population.problem.getObjectiveSpaceDimension();
		} else {
			LinkedList<Individual> pop = population.getPopulation();
			dim = (pop.getFirst()).os_dim;
		}
		
		Relation rel = new Relation(inds.length);
		for (int i=0; i< inds.length; i++) {
			for (int j=0;j<inds.length;j++) {
				boolean leq = true;
				for (int k=0;k < dim; k++) {
					if (inds[i].ov[k] > inds[j].ov[k]) {
						leq = false;
						break;
					}					
				}				
				if (leq) {
					rel.setinrelation(i,j, true);
				} else {
					rel.setinrelation(i,j,false);
				}
			}
		}
		return rel;
	}

	public Relation getDominanceRelation() {
		return dominanceRelation;
	}
	
	public int getDecisionSpaceDimension() {
		return ds_dim;
	}

	public int getObjectiveSpaceDimension() {
		return os_dim;
	}

	public Population getPopulation() {
		return pop;
	}

	public Problem getProblem() {
		return problem;
	}

	public Relation[] getRelations() {
		return relations;
	}
	
	// Delete after testing:
	public void printboolarray(boolean[] a) {
		for (int i=0;i<a.length;i++) {
			if (a[i]) {
				System.out.print(1);
			} else {
				System.out.print(0);
			}
		}
		System.out.println();
	}

}
