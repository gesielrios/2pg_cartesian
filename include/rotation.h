#ifndef OLD_ROTATION_TYPE_H
#define OLD_ROTATION_TYPE_H

#include "protein_type.h"

void rotation_psi(protein_t *prot, const int *num_res_first, const float *angle);
void rotation_phi(protein_t *prot, const int *num_res_first, const float *angle);
void rotation_omega(protein_t *prot, const int *num_res_first, const float *angle);

#endif