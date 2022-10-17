#include "libft.h"

char	*ft_strrchr(const char *str, int c)
{
	int		idx;

	idx = ft_strlen((char *)str) + 1;
	while (idx--)
	{
		if (*(str + idx) == c)
			return ((char *)(str + idx));
	}
	return (0);
}
/*
#include <stdio.h>
#include <string.h>
int main(void)
{
    char csrc[100] = "hhhhllll";
    // char csrc2[100] = "hhhhllll";

    // ft_memmove(csrc+3, csrc+2, 0);
    // memmove (csrc2+3, csrc2+2, 0);
    printf("%p\n", ft_strrchr(csrc, 'k'));
    printf("%p", strrchr (csrc, 'k'));
    return 0;
}*/
