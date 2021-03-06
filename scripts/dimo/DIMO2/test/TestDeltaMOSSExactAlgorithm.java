
package test;

import conflicts.DeltaMOSSExactAlgorithm;
import conflicts.FilePopulation;
import conflicts.FileProblem;
import conflicts.Individual;
import conflicts.Relation;
import conflicts.sets.ObjectiveSet;
import conflicts.sets.SetOfObjectiveSets;
import junit.framework.TestCase;

public class TestDeltaMOSSExactAlgorithm extends TestCase {

	/*
	 * Test method for 'conflicts.sets.SetOfObjectiveSets.union_ExactAlgo(SetOfIntSets)'
	 */
	public void testUnion_ExactAlgo() {
		SetOfObjectiveSets soos1 = new SetOfObjectiveSets();		
		int[] A1 = {1,2};
		int[] A2 = {1,2,3};
		int[] A3 = {1,2,4};
		ObjectiveSet setA1 = new ObjectiveSet(A1, 5, 1);
		ObjectiveSet setA2 = new ObjectiveSet(A2, 5, 0);
		ObjectiveSet setA3 = new ObjectiveSet(A3, 5, 0.5);
		soos1.add(setA1);
		soos1.add(setA2);
		soos1.add(setA3);
		// -------------------------------
		SetOfObjectiveSets soos2 = new SetOfObjectiveSets();
		int[] B1 = {1};
		int[] B2 = {2,3};
		int[] B3 = {2,3,4};
		int[] B4 = {1,2,3};
		ObjectiveSet setB1 = new ObjectiveSet(B1, 5, 3);
		ObjectiveSet setB2 = new ObjectiveSet(B2, 5, 2.5);
		ObjectiveSet setB3 = new ObjectiveSet(B3, 5, 0);
		ObjectiveSet setB4 = new ObjectiveSet(B4, 5, 2);
		soos2.add(setB1);
		soos2.add(setB2);
		soos2.add(setB3);
		soos2.add(setB4);
		// ------------------------------
		int[] res1 = {1,2};
		int[] res2 = {1,2,3};
		int[] res3 = {1,2,3,4};
		ObjectiveSet setRes1 = new ObjectiveSet(res1, 5, 3);
		ObjectiveSet setRes2 = new ObjectiveSet(res2, 5, 2);
		ObjectiveSet setRes3 = new ObjectiveSet(res3, 5, 0);
		// ------------------------------
		DeltaMOSSExactAlgorithm.union_ExactAlgo(soos1, soos2, 0, 0);
		// is the union equal to the expected result?
		assertTrue(soos1.contains(setRes1));
		soos1.remove(setRes1);
		assertTrue(soos1.contains(setRes2));
		soos1.remove(setRes2);
		assertTrue(soos1.contains(setRes3));
		soos1.remove(setRes3);		
		assertTrue(soos1.isEmpty());		
	}
	
	/*
	 * Test method for 'conflicts.sets.SetOfObjectiveSets.union_ExactAlgo(SetOfIntSets)'
	 */
	public void testUnion_ExactAlgo_2() {
		SetOfObjectiveSets soos1 = new SetOfObjectiveSets();		
		int[] A1 = {1};
		int[] A2 = {2};
		int[] A3 = {3};
		int[] A4 = {1,2};
		int[] A5 = {1,3};
		int[] A6 = {2,3};
		int[] A7 = {1,2,3};
		ObjectiveSet setA1 = new ObjectiveSet(A1, 5, 4);
		ObjectiveSet setA2 = new ObjectiveSet(A2, 5, 5);
		ObjectiveSet setA3 = new ObjectiveSet(A3, 5, 6);
		ObjectiveSet setA4 = new ObjectiveSet(A4, 5, 3);
		ObjectiveSet setA5 = new ObjectiveSet(A5, 5, 2);
		ObjectiveSet setA6 = new ObjectiveSet(A6, 5, 1);
		ObjectiveSet setA7 = new ObjectiveSet(A7, 5, 0);
		soos1.add(setA1);
		soos1.add(setA2);
		soos1.add(setA3);
		soos1.add(setA4);
		soos1.add(setA5);
		soos1.add(setA6);
		soos1.add(setA7);
		// -------------------------------
		SetOfObjectiveSets soos2 = new SetOfObjectiveSets();
		int[] B1 = {1};
		int[] B2 = {2};
		int[] B3 = {3};
		int[] B4 = {1,2};
		int[] B5 = {1,3};
		int[] B6 = {2,3};
		ObjectiveSet setB1 = new ObjectiveSet(B1, 5, 4);
		ObjectiveSet setB2 = new ObjectiveSet(B2, 5, 3);
		ObjectiveSet setB3 = new ObjectiveSet(B3, 5, 2);
		ObjectiveSet setB4 = new ObjectiveSet(B4, 5, 1);
		ObjectiveSet setB5 = new ObjectiveSet(B5, 5, 0);
		ObjectiveSet setB6 = new ObjectiveSet(B6, 5, 1);
		soos2.add(setB1);
		soos2.add(setB2);
		soos2.add(setB3);
		soos2.add(setB4);
		soos2.add(setB5);
		soos2.add(setB6);
		// ------------------------------
		int[] res1 = {1};
		int[] res2 = {2};
		int[] res3 = {3};
		int[] res4 = {1,2};
		int[] res5 = {1,3};
		int[] res6 = {2,3};
		int[] res7 = {1,2,3};
		ObjectiveSet setRes1 = new ObjectiveSet(res1, 5, 4);
		ObjectiveSet setRes2 = new ObjectiveSet(res2, 5, 5);
		ObjectiveSet setRes3 = new ObjectiveSet(res3, 5, 6);
		ObjectiveSet setRes4 = new ObjectiveSet(res4, 5, 3);
		ObjectiveSet setRes5 = new ObjectiveSet(res5, 5, 2);
		ObjectiveSet setRes6 = new ObjectiveSet(res6, 5, 1);
		ObjectiveSet setRes7 = new ObjectiveSet(res7, 5, 0);
		// ------------------------------
		DeltaMOSSExactAlgorithm.union_ExactAlgo(soos1, soos2, 0, 0);
		// is the union equal to the expected result?
		assertTrue(soos1.contains(setRes1));
		soos1.remove(setRes1);
		assertTrue(soos1.contains(setRes2));
		soos1.remove(setRes2);
		assertTrue(soos1.contains(setRes3));
		soos1.remove(setRes3);
		assertTrue(soos1.contains(setRes4));
		soos1.remove(setRes4);
		assertTrue(soos1.contains(setRes5));
		soos1.remove(setRes5);
		assertTrue(soos1.contains(setRes6));
		soos1.remove(setRes6);
		assertTrue(soos1.contains(setRes7));
		soos1.remove(setRes7);
		assertTrue(soos1.isEmpty());		
	}
	
	public void testExactAlgorithmGivenK() {
		/* preparing input for constructor of DeltaMOSSExactAlgorithm */
		FileProblem fp = new FileProblem("test/testExactAlgo1.txt");
		FilePopulation pop = new FilePopulation(fp);
		int os_dim = 3;
		Relation[] relations = new Relation[3];
		
		double[][] points = new double[3][3];
		double[][] temppoints = fp.getPoints();
		Individual[] inds = new Individual[3];
		inds[0] = new Individual();
		
		for(int i=0; i<points.length; i++) {
			for (int j=0; j<points[0].length; j++) {
				points[i][j] = temppoints[i][j+1]; 
			}
		}

		for (int i=0; i<3; i++) {
			relations[i] = new Relation(i, inds.length);
			for (int j=0; j< inds.length; j++) {
				for (int k=0;k<inds.length;k++) {
					if (points[j][i] <= points[k][i]) {
						relations[i].setinrelation(j,k, true);
					} else {
						relations[i].setinrelation(j,k, false);
					}
				}
			}
		}

		DeltaMOSSExactAlgorithm dmea = new DeltaMOSSExactAlgorithm(pop, os_dim, relations);
		int[] A = {0,2};
		ObjectiveSet exactoutput = new ObjectiveSet(A, 3, 0);
		assertTrue(exactoutput.theSame(dmea.performExactAlgorithm()));
		assertTrue(exactoutput.theSame(dmea.performExactAlgorithmGivenK(2)));
		int[] B = {0};
		ObjectiveSet exactoutputwithgivenk = new ObjectiveSet(B, 3, 2);
		assertTrue(exactoutputwithgivenk.theSame(dmea.performExactAlgorithmGivenK(1)));
		int[] C = {0,1};
		ObjectiveSet exactoutputwithgivendelta1 = new ObjectiveSet(C, 3, 1);
		int[] D = {0,2};
		ObjectiveSet exactoutputwithgivendelta2 = new ObjectiveSet(D, 3, 0);
		assertTrue(exactoutputwithgivendelta1.theSame(dmea.performExactAlgorithmGivenDelta(1)) ||
				exactoutputwithgivendelta2.theSame(dmea.performExactAlgorithmGivenDelta(1)));
	}
	
	public void testExactAlgorithmGivenDelta() {
		/* preparing input for constructor of DeltaMOSSExactAlgorithm */
		FileProblem fp = new FileProblem("test/testExactAlgo2.txt");
		FilePopulation pop = new FilePopulation(fp);
		int os_dim = 4;
		Relation[] relations = new Relation[4];
		
		double[][] points = new double[3][4];
		double[][] temppoints = fp.getPoints();
		Individual[] inds = new Individual[3];
		
		for(int i=0; i<points.length; i++) {
			for (int j=0; j<points[0].length; j++) {
				points[i][j] = temppoints[i][j+1]; 
			}
		}

		for (int i=0; i<4; i++) {
			relations[i] = new Relation(i, inds.length);
			for (int j=0; j< inds.length; j++) {
				for (int k=0;k<inds.length;k++) {
					if (points[j][i] <= points[k][i]) {
						relations[i].setinrelation(j,k, true);
					} else {
						relations[i].setinrelation(j,k, false);
					}
				}
			}
		}

		DeltaMOSSExactAlgorithm dmea = new DeltaMOSSExactAlgorithm(pop, os_dim, relations);
		int[] A1 = {0,1,2};
		int[] A2 = {0,2,3};
		ObjectiveSet outputA1 = new ObjectiveSet(A1, 4, 0);
		ObjectiveSet outputA2 = new ObjectiveSet(A2, 4, 0);
		ObjectiveSet output = dmea.performExactAlgorithm();
		assertTrue(outputA1.theSame(output) || outputA2.theSame(output));
		output = dmea.performExactAlgorithmGivenDelta(0);
		assertTrue(outputA1.theSame(output) || outputA2.theSame(output));
		output = dmea.performExactAlgorithmGivenDelta(0.5);
		assertTrue(outputA1.theSame(output) || outputA2.theSame(output));
		int[] A3 = {2,3};
		ObjectiveSet outputA3 = new ObjectiveSet(A3, 4, 1);
		output = dmea.performExactAlgorithmGivenDelta(1.2);
		assertTrue(outputA3.theSame(output));
		int[] B0 = {0};
		int[] B1 = {1};
		int[] B2 = {2};
		int[] B3 = {3};
		ObjectiveSet output0 = new ObjectiveSet(B0, 4, 3);
		ObjectiveSet output1 = new ObjectiveSet(B1, 4, 3);
		ObjectiveSet output2 = new ObjectiveSet(B2, 4, 3);
		ObjectiveSet output3 = new ObjectiveSet(B3, 4, 3);
		output = dmea.performExactAlgorithmGivenDelta(10);
		assertTrue(output0.theSame(output) ||
				output1.theSame(output) ||
				output2.theSame(output) ||
				output3.theSame(output));
	}
	
	
	public static void main(String[] args) {
		junit.swingui.TestRunner.run(TestDeltaMOSSExactAlgorithm.class);
	}

}
