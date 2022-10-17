#include "libft.h"

size_t	ft_strlcpy(char *dest, const char *src, size_t size)
{
	size_t	leng;
	size_t	i;

	leng = ft_strlen(src);
	i = 0;
	if (!dest || !src)
		return (0);
	while (i < size - 1 && src[i])
	{
		dest[i] = src[i];
		i++;
	}
	if (size)
	{
		dest[i] = '\0';
	}
	return (leng);
}
/*
#include <string.h>
#include <stdio.h>
int main (void)
{
	char csrc[100] = "d";
    char dest[100] = "hdsdsh";
	char csrc2[100] = "d";
    char dest2[100] = "hdsdsh";

    // ft_memmove(csrc+3, csrc+2, 0); 
    // memmove (csrc2+3, csrc2+2, 0);
    printf("%d\n", ft_strlcpy(dest, csrc, 0));
    printf("%d", ft_strlcpy2 (dest2, csrc2, 0));
    return 0;
}*/