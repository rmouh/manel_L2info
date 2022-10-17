#include "libft.h"

void    *ft_memchr(const void *s, int c, size_t n)
{
    unsigned char *p = (unsigned char*)s;
    unsigned char *isCharFind = NULL;
    while((s!= NULL) && (n--))
    {
        if( *p != (unsigned char)c)
            p++;
        else
        {
            isCharFind = p;
            break;
        }
    }
    return isCharFind;
}

#include <string.h>
#include <stdio.h>

int main(void)
{
    char csrc[100] = "hhffFhhl70lll";
    char csrc2[100] = "hhffFhhl70lll";

    // ft_memmove(csrc+3, csrc+2, 0);
    // memmove (csrc2+3, csrc2+2, 0);
    printf("%s\n", ft_memchr3(csrc,51, 77));
    printf("%s", memchr (csrc2, 51, 77));
    return 0;
}
