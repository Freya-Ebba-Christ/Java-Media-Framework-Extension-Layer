#include <vm_meg.h>


int main()
{
    Vec_DP x(10);
	Mat_DP H(5, 10);

	Vec_DP y = bl_matrix_multiply(H, x);
    
    return 0;
}
