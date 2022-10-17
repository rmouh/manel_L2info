#include "libft.h"

void *ft_memcpy(void *dest, const void *src, size_t n)
{
	int	i;
    char *csrc;
    char *cdest;

    csrc = (char *)src;
    cdest = (char *)dest;
	i = 0;

	while (csrc[i] && i < n)
	{
		cdest[i] = csrc[i];
		i++;
	}
	while (i < n)
	{
		cdest[i] = '\0';
		i++;
	}
	return (cdest);
}
/*
#include <string.h>
#include <stdio.h>
int main (void)
{
	char csrc[100] = "hhhhllll";
    char csrc2[100] = "hhhhllll";

    // ft_memmove(csrc+3, csrc+2, 0);
    // memmove (csrc2+3, csrc2+2, 0);
    printf("%s\n", ft_memcpy(csrc, "k", 2));
    printf("%s", memcpy (csrc2, "k", 2));
    return 0;
}*/