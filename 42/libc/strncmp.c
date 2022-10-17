#include "libft.h"

int ft_strncmp(const void *s1, const void *s2, size_t n)
{
	if (n != 0)
    {
		unsigned char *p1;
	    unsigned char *p2;
        
        p1 = (unsigned char *)s1;
        p2 = (unsigned char *)s2;
		do {
			if (*p1++ != *p2++)
				return (*--p1 - *--p2);
		} while (--n != 0);
	}
	return (0);
}
/*
#include <string.h>
#include <stdio.h>
int main(int argc ,char argv[])
{
    //void *t1 [] = "hloo";
    //void *t2 [] = "dfds";
    //char *r =ft_strncmp(t, 66, 3);
    printf("output : %d \n",ft_strncmp("", "", 0));
	printf("output : %d \n",strncmp("", "", 0));
}*/

