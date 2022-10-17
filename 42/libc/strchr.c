#include "libft.h"

char	*ft_strchr(const char *s, int c)
{
	while (*s != c)
	{
		if (*s == '\0')
			return (0);
		s++;
	}
	return (char *)s;
}
/*
#include <string.h>


int main(void)
{
    // char csrc[100] = "hhhhllll";
    // char csrc2[100] = "hhhhllll";

    // ft_memmove(csrc+3, csrc+2, 0);
    // memmove (csrc2+3, csrc2+2, 0);
    printf("%s\n", ft_strchr("hhhhllll", 'l'));
    printf("%s", strchr ("hhhhllll", 'l'));
    return 0;
}*/