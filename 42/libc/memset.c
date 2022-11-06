#include "libft.h"


void	*ft_memset(void *str, int c, size_t n)
{
	size_t		idx;

	idx = 0;
	while (*((unsigned char *)str))
	{
		*((unsigned char *)str + idx) = c;
		idx++;
	}
	return (str);
}
/*
int main(int argc ,char argv[])
{
    void *t [100];
    char *r =my_memset(t, 66, 3);
    printf("output : %d %d %d \n", r[0], r[1], r[2]);
}*/