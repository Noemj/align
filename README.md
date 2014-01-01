align
=====

Computes the optimal sequence alignment of two strings, such as the sequences of nucleotides within two DNA molecules.

The computation is implemented via dynamic programming to avoid recomputing same quantities multiple times. A numerical score representing the edit distance is produced. The penalty per gap is 2, per mismatch 1 and per match 0.

As an example, consider strings AACAGTTACC and TAAGGTCA. Two possible alignments are:

| A | A | C | A | G | T | T | A | C | C |
|---|---|---|---|---|---|---|---|---|---|
| **T** | **A** | **A** | **G** | **G** | **T** | **C** | **A** | **-** | **-** |
| 1 | 0 | 1 | 1 | 0 | 0 | 1 | 0 | 2 | 2 |

| A | A | C | A | G | T | T | A | C | C |
|---|---|---|---|---|---|---|---|---|---|
| **T** | **A** | **-** | **A** | **G** | **G** | **T** | **-** | **C** | **A** |
| 1 | 0 | 2 | 0 | 0 | 1 | 0 | 2 | 0 | 1 |

The first alignment has a score of 8, while the score of the second alignment is 7.
