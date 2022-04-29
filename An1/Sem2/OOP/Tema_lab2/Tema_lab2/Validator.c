#include "Domain.h"

int validation(int code, float concentration, int quantity) {
	if (code < 0)
		return 1;
	if (concentration < 0)
		return 2;
	if (quantity < 0)
		return 4;
	return 0;
}