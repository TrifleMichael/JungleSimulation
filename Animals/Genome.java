package Animals;

import Utilities.Vector2d;

import java.util.concurrent.ThreadLocalRandom;


public class Genome {
    final int[] genome;
    final int genomeLength = 32;

    public int getRandomGene() {
        return this.genome[ThreadLocalRandom.current().nextInt(0, this.genomeLength)];
    }

    public Genome() {
        this.genome = new int[this.genomeLength];
        for(int i = 0; i < this.genomeLength; i++)
            this.genome[i] = ThreadLocalRandom.current().nextInt(0, 8);
        repairGenome();
    }



    public Genome(Genome parent1, Genome parent2) {
        int[] sequence1;
        int[] sequence2;

        // The sequence1 array will pass 2 out of 3 subsequences.
        // Here the parent genomes can be randomly swapped to assure a random parent gives 2 sequences to child.
        if (ThreadLocalRandom.current().nextInt(0, 2) != 0) {
            sequence1 = parent1.getGenomeSequence().clone();
            sequence2 = parent2.getGenomeSequence().clone();
        }
        else {
            sequence2 = parent1.getGenomeSequence().clone();
            sequence1 = parent2.getGenomeSequence().clone();
        }


        // Randomly generating two distinct genome split points.
        int split1 = ThreadLocalRandom.current().nextInt(0, this.genomeLength);
        int split2;
        do {
            split2 = ThreadLocalRandom.current().nextInt(0, this.genomeLength);
        } while (split2 == split1);

        // Assuring split1 < split2
        if (split1 > split2) {
            int storage = split1;
            split1 = split2;
            split2 = storage;
        }

        int[] result = new int[32];
        int parent2Section = ThreadLocalRandom.current().nextInt(0, 3); // Randomly choosing section for sequence 2
        switch (parent2Section) {
            case 0:
                for(int i = 0; i < split1; i++)                    result[i] = sequence2[i];
                for(int i = split1; i < this.genomeLength; i++)    result[i] = sequence1[i];
                break;
            case 1:
                for(int i = 0; i < split1; i++)                     result[i] = sequence1[i];
                for(int i = split1; i < split2; i++)                result[i] = sequence2[i];
                for(int i = split2; i < this.genomeLength; i++)     result[i] = sequence1[i];
                break;
            case 2:
                for(int i = 0; i < split1; i++)                     result[i] = sequence1[i];
                for(int i = split2; i < this.genomeLength; i++)     result[i] = sequence2[i];
                break;
        }

        this.genome = result;
        repairGenome();
    }

    // Returns true if genome contains all genes
    // Otherwise returns false
    public boolean testGenome() {
        boolean[] contains = new boolean[8];
        for(int i = 0; i < 8; i++) {
            contains[i] = false;
        }
        for(int gene : this.genome) {
            contains[gene] = true;
        }
        for(boolean con : contains) {
            if (!con)
                return false;
        }
        return true;
    }

    private void repairGenome() {

        int[] numOfGenes = new int[8];
        for(int i = 0; i < 8; i++)
            numOfGenes[i] = 0;

        for(int gene : this.genome)
            numOfGenes[gene] += 1;

        int rand;
        for(int i = 0; i < 8; i++) {

            while (numOfGenes[i] == 0) {                                                    // If 'i' gene doesn't exist in genome
                rand = ThreadLocalRandom.current().nextInt(0, getGenomeLength());     // Select a random part of genome

                if (numOfGenes[this.genome[rand]] > 1) {                                    // If the random part isn't the only instance of its gene
                    numOfGenes[this.genome[rand]]--;
                    this.genome[rand] = i;                                                  // Then swap that gene for the non existing
                    numOfGenes[i]++;
                }
            }
        }
    }

    public int[] getGenomeSequence() {
        return this.genome;
    }

    public int getGenomeLength() {
        return this.genomeLength;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int gene : this.genome)
            result.append(gene);
        return result.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Genome))
            return false;

        int[] genes = new int[8];
        for(int gene : ((Genome) other).getGenomeSequence())
            genes[gene]++;
        for(int gene : this.genome)
            genes[gene]--;

        for(int geneNum : genes)
            if(geneNum != 0)
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        int[] genes = new int[8];
        for(int gene : genome)
                genes[gene]++;

        int result = 0;
        for(int i : genes) {
            result += i;
            result *= 10;
        }
        return result;
    }
}
